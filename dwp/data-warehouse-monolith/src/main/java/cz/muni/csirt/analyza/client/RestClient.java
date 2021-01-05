package cz.muni.csirt.analyza.client;

import cz.muni.csirt.analyza.entity.*;
import cz.muni.csirt.analyza.util.uuid.UUIDUtil;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Class that provides a RESTful client for api of data warehouse.
 *
 * @author David Brilla*xbrilla*469054
 */
public class RestClient {

    private static final String REST_URI = "http://147.251.124.63:8080/";
    private Client client = ClientBuilder.newClient();

    // --------------------------------------- get

    public GenericObject getGenericObject(UUID uuid) {
        return client.target(REST_URI)
                .path("generic-objects/")
                .path(uuid.toString())
                .request(MediaType.APPLICATION_JSON)
                .get(GenericObject.class);
    }

    /*
    public DataFile getDataFile(UUID uuid) {
        return client.target(REST_URI)
                .path("generic-objects")
                .path(uuid.toString())
                .request(MediaType.APPLICATION_JSON)
                .get(DataFile.class);
    }

    public InputStream getDataFileStream(UUID uuid) {
        return client.target(REST_URI)
                .path("generic-objects")
                .path("data-files")
                .path(uuid.toString())
                .request(MediaType.APPLICATION_OCTET_STREAM)
                .get(InputStream.class);
    }
    */

    public Link getLink(UUID uuid) {
        return client.target(REST_URI)
                .path("links")
                .path(uuid.toString())
                .request(MediaType.APPLICATION_JSON)
                .get(Link.class);
    }

    // --------------------------------------- delete

    public Response deleteGenericObject(UUID uuid) {
        return client.target(REST_URI)
                .path("generic-objects")
                .path(uuid.toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();
    }

    /*
    public Response deleteDataFile(UUID uuid) {
        return client.target(REST_URI)
                .path("generic-objects")
                .path("data-files")
                .path(uuid.toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();
    }
    */

    public Response deleteLink(UUID uuid) {
        return client.target(REST_URI)
                .path("links")
                .path(uuid.toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();
    }

    // --------------------------------------- post

    public UUID createGenericObject(String name, String organization, ObjectType type, String caseId) {
        String typeString = "unknown";
        if (type != null) {
            typeString = type.getType();
        }
        if (caseId == null || caseId.equals("null")) {
            caseId = "default";
        }
        return UUIDUtil.getUUIDFromJson(
                client.target(REST_URI)
                        .path("generic-objects")
                        .queryParam("name", name)
                        .queryParam("organization", organization)
                        .queryParam("type", typeString)
                        .queryParam("caseId", caseId)
                        .request(MediaType.APPLICATION_JSON)
                        .post(null)
                        .readEntity(String.class));
    }

    /*
    public UUID createDataFile(InputStream inputStream, String name, String caseId) throws IOException, URISyntaxException {
        if (caseId == null || caseId.equals("null")) {
            caseId = "default";
        }
        List<NameValuePair> arguments = new ArrayList<>(2);
        arguments.add(new BasicNameValuePair("name", name));
        arguments.add(new BasicNameValuePair("caseId", caseId));
        URIBuilder uriBuilder = new URIBuilder(REST_URI + "generic-objects/data-files").addParameters(arguments);
        HttpPost post = new HttpPost(uriBuilder.build());
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("file", inputStream, ContentType.DEFAULT_BINARY, name);
        post.setEntity(builder.build());
        HttpResponse response = HttpClients.createDefault().execute(post);
        return UUIDUtil.getUUIDFromJson(IOUtils.toString(response.getEntity().getContent(), "UTF-8"));
    }
    */

    public UUID createLink(UUID left, UUID right, LinkType type, Boolean oriented, String caseId) {
        String typeString = "unknown";
        if (type != null) {
            typeString = type.getType();
        }
        if (caseId == null || caseId.equals("null")) {
            caseId = "default";
        }
        if (oriented == null) {
            oriented = false;
        }
        return UUIDUtil.getUUIDFromJson(
                client.target(REST_URI)
                        .path("links")
                        .queryParam("left", left.toString())
                        .queryParam("right", right.toString())
                        .queryParam("type", typeString)
                        .queryParam("oriented", oriented)
                        .queryParam("caseId", caseId)
                        .request(MediaType.APPLICATION_JSON)
                        .post(null)
                        .readEntity(String.class));
    }

    // --------------------------------------- put

    public Response updateUserProperties(UUID uuid, String key, String value) {
        return client.target(REST_URI)
                .path("generic-objects")
                .path(uuid.toString())
                .queryParam("key", key)
                .queryParam("value", value)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(new UserProperty()));
    }

    /*
    public Response updateDataFile(UUID uuid, InputStream inputStream) throws IOException {
        HttpPut put = new HttpPut(REST_URI + "generic-objects/data-files/" + uuid.toString());
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("file", inputStream, ContentType.DEFAULT_BINARY,  getGenericObject(uuid).getName());
        put.setEntity(builder.build());
        HttpResponse response = HttpClients.createDefault().execute(put);
        return Response.ok(response.getEntity()).build();
    }
    */

    // --------------------------------------- alternatives

    public UUID createGenericObject(String name) {
        return createGenericObject(name, null, null, null);
    }

    public UUID createGenericObject(String name, String organization, ObjectType type) {
        return createGenericObject(name, organization, type, null);
    }

    public UUID createGenericObject(String name, ObjectType type) {
        return createGenericObject(name, null, type,null);
    }

    /*
    public UUID createDataFile(InputStream inputStream, String name) throws IOException, URISyntaxException {
        return createDataFile(inputStream, name, null);
    }

    public UUID createDataFile(File file, String caseId) throws IOException, URISyntaxException {
        return createDataFile(new FileInputStream(file), file.getName(), caseId);
    }

    public UUID createDataFile(File file) throws IOException, URISyntaxException {
        return createDataFile(new FileInputStream(file), file.getName(), null);
    }
    */

    public UUID createLink(UUID left, UUID right, LinkType type, Boolean oriented) {
        return createLink(left, right, type, oriented, null);
    }

    public UUID createLink(UUID left, UUID right) {
        return createLink(left, right, null, null, null);
    }

    public UUID createLink(UUID left, UUID right, String caseId) {
        return createLink(left, right, null, null, caseId);
    }

    /*
    public Response updateDataFile(UUID uuid, File file) throws IOException {
        return updateDataFile(uuid, new FileInputStream(file));
    }
    */
}
