package cz.muni.csirt.analyza.service;

import cz.muni.csirt.analyza.entity.GenericObject;
import cz.muni.csirt.analyza.entity.Link;
import cz.muni.csirt.analyza.entity.LinkType;
import cz.muni.csirt.analyza.repository.GenericObjectRepository;
import cz.muni.csirt.analyza.repository.LinkRepository;
import cz.muni.csirt.analyza.repository.LinkTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of LinkService interface.
 *
 * @author David Brilla*xbrilla*469054
 */
@Service
public class LinkServiceImpl implements LinkService {

    private LinkRepository linkRepository;
    private GenericObjectRepository genericObjectRepository;
    private LinkTypeRepository linkTypeRepository;

    @Autowired
    public LinkServiceImpl(LinkRepository linkRepository, GenericObjectRepository genericObjectRepository,
                           LinkTypeRepository linkTypeRepository) {
        this.linkRepository = linkRepository;
        this.genericObjectRepository = genericObjectRepository;
        this.linkTypeRepository = linkTypeRepository;
    }

    @Transactional
    @Override
    public Link create(UUID left, UUID right, String type, boolean oriented) {
        LinkType linkType = linkTypeRepository.findByType(type)
                .orElse(linkTypeRepository.save(new LinkType(type)));
        GenericObject leftObject = genericObjectRepository.findByUuid(left).orElseThrow(IllegalArgumentException::new);
        GenericObject rightObject = genericObjectRepository.findByUuid(right).orElseThrow(IllegalArgumentException::new);
        Link link = (Link) new Link(leftObject, rightObject, linkType, oriented);
        linkRepository.save(link);
        return link;
    }

    @Override
    public Link getByUuid(UUID uuid) {
        return linkRepository.findByUuid(uuid).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Link getByObjects(UUID left, UUID right, Optional<String> type, Optional<Boolean> oriented) {
        Collection<Link> links = new ArrayList<>();
        links.addAll(linkRepository.findAllByLeftUuid(left));
        for (Link link: links) {
            if (link.getRight().getUuid().equals(right)) {
                if (type.isPresent()) {
                    if (!link.getType().getType().equals(type)) {
                        return null;
                    }
                }
                if (oriented.isPresent()) {
                    if (!link.equals(oriented)) {
                        return null;
                    }
                }
                return link;
            }
        }
        return null;
    }

    @Override
    public Collection<Link> getAll() {
        return linkRepository.findAll(null);
    }

    @Transactional
    @Override
    public void deleteByUuid(UUID uuid) {
        Link link = linkRepository.findByUuid(uuid).orElseThrow(IllegalArgumentException::new);
        link.setExpired(LocalDateTime.now());
        linkRepository.save(link);
    }

    @Override
    public Collection<Link> filter(UUID uuid) {
        if (!genericObjectRepository.findByUuid(uuid).isPresent()) {
            throw new IllegalArgumentException();
        }
        Collection<Link> result = new ArrayList<>();
        result.addAll(linkRepository.findAllByLeftUuid(uuid));
        result.addAll(linkRepository.findAllByRightUuid(uuid));
        return result;
    }
}
