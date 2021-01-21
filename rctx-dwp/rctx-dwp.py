from typing import Optional

from types_validation import validate_link, validate_generic_object

from fastapi import FastAPI, status, Response
from pydantic import BaseModel
import requests as req
import logging



API_ENDPOINT_OBJECTS = "http://192.168.16.184:8080/generic-objects/"
API_ENDPOINT_LINKS = "http://192.168.16.184:8080/links/"

app = FastAPI()

root_logger= logging.getLogger()
root_logger.setLevel(logging.WARNING)
handler = logging.FileHandler('rctx.log', 'w', 'utf-8')
handler.setFormatter(logging.Formatter('%(name)s %(message)s'))
root_logger.addHandler(handler)

class Entity(BaseModel):
    system: str
    type: str
    registeredId: str

class UserProperty(BaseModel):
    key: str
    value: str

class EntityUpdate(BaseModel):
    registeredId: str
    userProperty: UserProperty

class EntityDelete(BaseModel):
    registeredId: str
    recursive: Optional[bool]

class LinkUpdate(BaseModel):
    leftRegisteredId: str
    rightRegisteredId: str

class LinkDelete(BaseModel):
    leftRegisteredId: str
    rightRegisteredId: str

class UpdateParams(BaseModel):
    uuid: str
    key: str
    value: str

class DeleteParams(BaseModel):
    uuid: str
    recursive: bool

class Link(BaseModel):
    left: Optional[str]
    right: Optional[str]
    type: str
    oriented: bool

class LinkArgs(BaseModel):
    type: str
    oriented: bool

@app.post("/create-link/", status_code=201)
async def create_link(entityLeft: Entity, entityRight: Entity, linkArgs: LinkArgs, response: Response):
    try:
        validate_link(entityLeft.system, entityLeft.type, entityRight.system, entityRight.type)
    except Exception as inst:
        response.status_code = 410
        return response

    leftEntityCreated = False
    rightEntityCreated = False
    linkCreated = False

    left = req.get(url = API_ENDPOINT_OBJECTS + "?registeredId=" + entityLeft.registeredId)
    left_data = left.json()
    if not left_data:
        left_resp = req.post(url = API_ENDPOINT_OBJECTS, data = entityLeft.dict()).json()
        left_uuid = left_resp["uuid"]
        leftEntityCreated = True
    else: 
        left_uuid = left_data[0]["uuid"]

    right = req.get(url = API_ENDPOINT_OBJECTS + "?registeredId=" + entityRight.registeredId)
    right_data = right.json()
    if not right_data:
        right_resp = req.post(url = API_ENDPOINT_OBJECTS, data = entityRight.dict()).json()
        right_uuid = right_resp["uuid"]
        rightEntityCreated = True
    else:
        right_uuid = right_data[0]["uuid"]
            
    
    l = req.get(url = API_ENDPOINT_LINKS + "link" + "?left=" + left_uuid + "&right=" + right_uuid)
    if not l.text:
        link = Link(left = left_uuid, right = right_uuid, type = linkArgs.type, oriented = linkArgs.oriented)
        l_uuid = req.post(url = API_ENDPOINT_LINKS, data = link.dict()).json()
        linkCreated = True

    if (leftEntityCreated):
        logging.warning("WARNING: Left entity is not yet created.\n" + "left entity id:" + entityLeft.registeredId + "\n" + "right entity id:" + entityRight.registeredId + "\n" + "link type:" + linkArgs.type + "\n")
    if ( not leftEntityCreated and not rightEntityCreated and not linkCreated):
        logging.warning("WARNING: Possible duplicated message.\n" + "left entity id:" + entityLeft.registeredId + "\n" + "right entity id:" + entityRight.registeredId + "\n" + "link type:" + linkArgs.type + "\n")
    if (leftEntityCreated and not rightEntityCreated):
        logging.warning("WARNING: One message can came before the other, kafka or producer issue.\n" + "left entity id:" + entityLeft.registeredId + "\n" + "right entity id:" + entityRight.registeredId + "\n" + "link type:" + linkArgs.type + "\n")


    return

@app.post("/modify-link/", status_code=201)
async def modify_link(linkUpdate: LinkUpdate,
                      userProperty: UserProperty, response: Response):
    left = req.get(url = API_ENDPOINT_OBJECTS + "?registeredId=" + linkUpdate.leftRegisteredId)
    left_data = left.json()
    if not left_data:
        response.status_code = 415
        return response
    else:
        left_uuid = left_data[0]["uuid"]

    right = req.get(url = API_ENDPOINT_OBJECTS + "?registeredId=" + linkUpdate.rightRegisteredId)
    right_data = right.json()
    if not right_data:
        response.status_code = 415
        return response
    else:
        right_uuid = right_data[0]["uuid"]
        
    l = req.get(url = API_ENDPOINT_LINKS + "link" + "?left=" + left_uuid + "&right=" + right_uuid)
    if not l.text:
        response.status_code = 415
        return response
    else:
        l_data = l.json()
        l_uuid = l_data["uuid"]

            
    
    link = req.put(url = API_ENDPOINT_LINKS + l_uuid, params = userProperty.dict())
    

@app.post("/delete-link/", status_code=201)
async def delete_link(linkDelete: LinkDelete, response: Response):
    left = req.get(url = API_ENDPOINT_OBJECTS + "?registeredId=" + linkDelete.leftRegisteredId)
    left_data = left.json()
    if not left_data:
        response.status_code = 415
        return response
    else:
        left_uuid = left_data[0]["uuid"]

    right = req.get(url = API_ENDPOINT_OBJECTS + "?registeredId=" + linkDelete.rightRegisteredId)
    right_data = right.json()
    if not right_data:
        response.status_code = 415
        return response
    else:
        right_uuid = right_data[0]["uuid"]

    l = req.get(url = API_ENDPOINT_LINKS + "link" + "?left=" + left_uuid + "&right=" + right_uuid)
    if not l.text:
        response.status_code = 415
        return response
    else:
        l_data = l.json()
        l_uuid = l_data["uuid"]
        d = req.delete(url = API_ENDPOINT_LINKS + l_uuid)

@app.post("/create-generic-object/", status_code=201)
async def create_entity(entity: Entity, response: Response):
    try:
        validate_generic_object(entity.system, entity.type)
    except Exception as inst:
        response.status_code = 410
        return response
    r = req.get(url = API_ENDPOINT_OBJECTS + "?registeredId=" + entity.registeredId)
    entity_data = r.json()
    if not entity_data:
        r = req.post(url = API_ENDPOINT_OBJECTS, data = entity.dict()).json()
        entity_uuid = r["uuid"]

@app.post("/modify-generic-object/", status_code=201)
async def modify_entity(updateEntity: EntityUpdate, response: Response):
    entity = req.get(url = API_ENDPOINT_OBJECTS + "?registeredId=" + updateEntity.registeredId)
    entity_data = entity.json()
    if not entity_data:
        response.status_code = 415
        return response
    else:
        entity_uuid = entity_data[0]["uuid"]
        userProperty = UserProperty(key = updateEntity.userProperty.key, value = updateEntity.userProperty.value)
        r = req.put(url = API_ENDPOINT_OBJECTS + entity_uuid, params= userProperty.dict())

@app.post("/delete-generic-object/", status_code=201)
async def delete_entity(entity: EntityDelete, response: Response):
    deleteEntity = req.get(url = API_ENDPOINT_OBJECTS + "?registeredId=" + entity.registeredId)
    entity_data = deleteEntity.json()
    print(entity_data)
    if not entity_data:
        response.status_code = 415
        return response
    else:
        entity_uuid = entity_data[0]["uuid"]
        delete_params = DeleteParams(uuid = entity_uuid, recursive = entity.recursive)
        d = req.delete(url = API_ENDPOINT_OBJECTS, params= delete_params.dict())

