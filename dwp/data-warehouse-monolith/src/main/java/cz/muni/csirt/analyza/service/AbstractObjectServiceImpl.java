package cz.muni.csirt.analyza.service;

import cz.muni.csirt.analyza.entity.AbstractObject;
import cz.muni.csirt.analyza.entity.GenericObject;
import cz.muni.csirt.analyza.entity.Link;
import cz.muni.csirt.analyza.entity.UserProperty;
import cz.muni.csirt.analyza.repository.AbstractObjectRepository;
import cz.muni.csirt.analyza.repository.GenericObjectRepository;
import cz.muni.csirt.analyza.repository.LinkRepository;
import cz.muni.csirt.analyza.repository.UserPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * Implementation of AbstractObjectService interface.
 *
 * @author David Brilla*xbrilla*469054
 */
@Service
public class AbstractObjectServiceImpl implements AbstractObjectService {

    private AbstractObjectRepository abstractObjectRepository;
    private UserPropertyRepository userPropertyRepository;

    @Autowired
    public AbstractObjectServiceImpl(AbstractObjectRepository abstractObjectRepository, UserPropertyRepository userPropertyRepository) {
        this.abstractObjectRepository = abstractObjectRepository;
        this.userPropertyRepository = userPropertyRepository;
    }

    @Transactional
    @Override
    public AbstractObject updateProperties(UUID uuid, String key, String value) {
        AbstractObject object = abstractObjectRepository.findByUuid(uuid).orElseThrow(IllegalArgumentException::new);

        Collection<UserProperty> properties = object.getProperties();
        Long valueLong;
        Double valueDouble;
        for (UserProperty property : properties) {
            if (property.getPropertyKey().equals(key)) {
                LocalDateTime time = LocalDateTime.now();
                if (value.toLowerCase().equals("null")) {
                    property.setExpired(time);
                    return abstractObjectRepository.save(object);
                } else {
                    property.setExpired(time);
                    properties.add(createUserProperty(object, time, key, value));
                    return abstractObjectRepository.save(object);
                }
            }
        }
        LocalDateTime time = LocalDateTime.now();
        properties.add(createUserProperty(object, time, key, value));
        return abstractObjectRepository.save(object);
    }

    private UserProperty createUserProperty(AbstractObject object, LocalDateTime time, String key, String value) {
        UserProperty newProperty;
        Long valueLong;
        Double valueDouble;
        try {
            valueDouble = Double.parseDouble(value);
            newProperty = new UserProperty(object, time, key, valueDouble);
        } catch (NumberFormatException ex) {
            try {
                valueLong = Long.parseLong(value);
                newProperty = new UserProperty(object, time, key, valueLong);
            } catch (NumberFormatException e) {
                newProperty = new UserProperty(object, time, key, value);
            }
        }
        return newProperty;
    }
}
