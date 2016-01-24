package com.adarsh.resources;

import com.adarsh.utils.GuiceInjector;
import org.atmosphere.annotation.Resume;
import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.websocket.WebSocketEventListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by adarsh.sharma on 23/01/16.
 */

@ManagedService(path = "/socket")
public class SocketResource {

    final private static Logger logger = LoggerFactory.getLogger(SocketResource.class);

//    @Inject
//    Machine machine;

//    @Inject
//    RabbitMQService rabbitMQService;

//    @Inject
//    Validator validator;

//    @Inject
//    private SessionFactory sessionFactory;

    public SocketResource() {
        GuiceInjector.getInjector().injectMembers(this);
    }

//
//    @Heartbeat
//    public void onHeartbeat(final AtmosphereResourceEvent event) {
//        logger.info("Heartbeat send by {}", event.getResource());
//    }

    @Resume
    public void onResume(final AtmosphereResource r) {
        logger.info("Browser resumed.", r.uuid());
    }

    @Ready
    public void onReady(final AtmosphereResource r) {

        String agentID = r.getRequest().getParameter("agent");
        String clientID = r.getRequest().getParameter("clientID");

        logger.info("onReady: Agent:" +  agentID + " Client-id: " + clientID + " uuid: " + r.uuid());

        assert agentID != null;
//        attachAgent(agentID, clientID, r);

        AppCloseEventListener appCloseEventListener = new AppCloseEventListener();
        r.addEventListener(appCloseEventListener);
    }

    @Disconnect
    public void onDisconnect(AtmosphereResourceEvent event) {

        logger.info("onDisconnect: Connection closed");
        String uuid = event.getResource().uuid();

        if (event.isCancelled()) {
            logger.info("onDisconnect: Browser unexpectedly disconnected ", uuid);
        } else if (event.isClosedByClient()) {
            logger.info("onDisconnect: Browser closed the connection ", uuid);
        } else   {
            logger.info("onDisconnect: Unknown Disconnect Event " + uuid );
        }

//        detachAgent(uuid);
    }

    @org.atmosphere.config.service.Message()
    public void onMessage(String rawMessage) throws IOException {

        logger.info(rawMessage + " was just sent.");

//        ObjectMapper objectMapper = new ObjectMapper();
//        ClientMessage message = objectMapper.readValue(rawMessage, ClientMessage.class);
//
//        Response response = validate(message);
//        if(response.getStatus() != 200) {
//            logger.error("Error in request.");
//            return;
//        }
//
//        logger.info("id:" + message.getId());
//        logger.info("data: " + message.getData() );
//        logger.info("sender: " + message.getSender());
//        logger.info("receiver: " + message.getReceiver());
//        logger.info("type: " + message.getType());
//        logger.info("call id: " + message.getCallID());
//        logger.info("uuid: " + message.getUuid());
//        logger.info("client id: " + message.getClientID());
//        logger.info("event: " + message.getEvent());
//
//
//        //Create the response
//        String id = message.getId();
//        Map<String, Object> responseParams = new HashMap<String, Object>();
//        responseParams.put("id",id);
//        ObjectMapper mapper = new ObjectMapper();
//        String responseStr = "ok";
//        try {
//            responseStr = mapper.writeValueAsString(responseParams);
//            logger.info("handleClientMessage: Message to be returned: " + responseStr);
//        } catch (Exception e) {
//            logger.error("handleClientMessage: Exception while serializing the message for socket: " + e.getMessage(),e);
//            logger.error("handleClientMessage: Couldn't send the message");
//        }
//
//        //handle the message
//        String type = message.getType();
//        if(type.equals(ClientMessageType.HEART_BEAT.name())) {
//
//            return;
//            //return Response.ok(responseStr).build();
//        } else if(type.equals(ClientMessageType.TRANSFER.name())) {
//
//            String callID = message.getCallID();
//            if(callID == null || callID.equals("")) {
//                logger.error("handleClientMessage: Call id is null or blank");
//                //return Response.status(Response.Status.BAD_REQUEST).entity(responseStr).build();
//                return;
//            }
//
//            String msg = formSocketMessage(message);
//            String receiver = message.getReceiver();
//
//            saveMessageIntoDB(message);
//            if(msg != null) {
//                try {
//                    rabbitMQService.push(msg, receiver);
//                } catch (Exception e) {
//                    logger.error("handleClientMessage: Couldn't send the message to: " + receiver + " message: " + msg);
//                    httpSocketMessageService.sendMessageThroughHTTP(receiver, msg);
//                }
//            } else {
//                logger.error("handleClientMessage: Couldn't send the msg as msg is null");
//            }
//        } else if(type.equals(ClientMessageType.CHAT.name())) {
//
//            String msg = formSocketMessage(message);
//            String receiver = message.getReceiver();
//
//            saveMessageIntoDB(message);
//            if(msg != null) {
//                try {
//                    rabbitMQService.push(msg, receiver);
//                } catch (Exception e) {
//                    logger.error("handleClientMessage: Couldn't send the message to: " + receiver + " message: " + msg);
//                    httpSocketMessageService.sendMessageThroughHTTP(receiver, msg);
//                }
//            } else {
//                logger.error("handleClientMessage: Couldn't send the msg as msg is null");
//            }
//        } else if(type.equals(ClientMessageType.SA_EVENTS.name())){
//
//            String eventData = message.getEvent();
//
//            //ClientEventDeserializer clientEventDeserializer = new ClientEventDeserializer();
//            //SimpleModule module = new SimpleModule("PolymorphicDataDeserializerModule",new Version(1,0,0,null,null,null));
//            //module.addDeserializer(ClientEvent.class, clientEventDeserializer);
//
//            ObjectMapper objectMapper1 = new ObjectMapper();
//            //objectMapper1.registerModule(module);
//
//            ClientEvent clientEvent = objectMapper1.readValue(eventData, ClientEvent.class);
//            if(clientEvent != null) {
//
//                clientEvent.handleEvent();
//            }
//        } else {
//
//            logger.error("handleClientMessage: Unspecified event from client");
//        }
        //return Response.ok(responseStr).build();
        return;
    }

    private class AppCloseEventListener extends WebSocketEventListenerAdapter {

        @Override
        public void onClose(WebSocketEvent event) {
            logger.info("Happy !! {}", event);
        }
    }

//    public void decreaseSocketCount(String host) {
//
//        HibernateBundle hibernateBundle = GuiceInjector.getInjector().getInstance(HibernateBundle.class);
//        SessionFactory sessionFactory = hibernateBundle.getSessionFactory();
//        final Session session = sessionFactory.openSession();
//        try {
//            ManagedSessionContext.bind(session);
//            session.beginTransaction();
//            try {
//                CtiHostDao ctiHostDao = new CtiHostDao(hibernateBundle);
//                ctiHostDao.removeConnectionCount(host);
//
//                final Transaction txn = session.getTransaction();
//                if (txn != null && txn.isActive()) {
//                    txn.commit();
//                }
//            } catch (Exception e) {
//                logger.error(e.getLocalizedMessage(), e);
//            }
//        } finally {
//            session.close();
//            ManagedSessionContext.unbind(sessionFactory);
//        }
//    }

//    public void removeAgentFromDB(String agentID, String host, String uuid) {
//
//        logger.info("Disabling the agent in global DB: " + agentID);
//        HibernateBundle hibernateBundle = GuiceInjector.getInjector().getInstance(HibernateBundle.class);
//        SessionFactory sessionFactory = hibernateBundle.getSessionFactory();
//        final Session session = sessionFactory.openSession();
//        try {
//            ManagedSessionContext.bind(session);
//            session.beginTransaction();
//            try {
//
//                AgentHostDao agentHostDao = new AgentHostDao(hibernateBundle);
//                agentHostDao.disableAgent(agentID,host, uuid);
//                final Transaction txn = session.getTransaction();
//                if (txn != null && txn.isActive()) {
//                    txn.commit();
//                }
//            } catch (Exception e) {
//                logger.error(e.getLocalizedMessage(), e);
//            }
//        } finally {
//            session.close();
//            ManagedSessionContext.unbind(sessionFactory);
//        }
//        logger.info("Disabled the agent in global DB: " + agentID);
//    }

//    public Boolean addAgentInDB(String agentID, String host, String uuid, String clientID) {
//
//        Boolean isNewlyAddedUUID = false;
//        logger.info("Enabling the agent in global DB: " + agentID);
//        HibernateBundle hibernateBundle = GuiceInjector.getInjector().getInstance(HibernateBundle.class);
//        SessionFactory sessionFactory = hibernateBundle.getSessionFactory();
//        final Session session = sessionFactory.openSession();
//        try {
//            ManagedSessionContext.bind(session);
//            session.beginTransaction();
//            try {
//                AgentHostDao agentHostDao = new AgentHostDao(hibernateBundle);
//                isNewlyAddedUUID = agentHostDao.setHost(agentID,host,uuid, clientID);
//                final Transaction txn = session.getTransaction();
//                if (txn != null && txn.isActive()) {
//                    txn.commit();
//                }
//            } catch (Exception e) {
//                logger.error(e.getLocalizedMessage(), e);
//            }
//        } finally {
//            session.close();
//            ManagedSessionContext.unbind(sessionFactory);
//        }
//        logger.info("Enabled the agent in global DB: " + agentID);
//        return isNewlyAddedUUID;
//    }
//
//    public void increaseSocketCount(String host) {
//
//        logger.info("increasing the count for websocket in host table");
//        HibernateBundle hibernateBundle = GuiceInjector.getInjector().getInstance(HibernateBundle.class);
//        SessionFactory sessionFactory = hibernateBundle.getSessionFactory();
//        final Session session = sessionFactory.openSession();
//        try {
//            ManagedSessionContext.bind(session);
//            session.beginTransaction();
//            try {
//                CtiHostDao ctiHostDao = new CtiHostDao(hibernateBundle);
//                ctiHostDao.addConnectionCount(host);
//
//                final Transaction txn = session.getTransaction();
//                if (txn != null && txn.isActive()) {
//                    txn.commit();
//                }
//            } catch (Exception e) {
//                logger.error(e.getLocalizedMessage(), e);
//            }
//        } finally {
//            session.close();
//            ManagedSessionContext.unbind(sessionFactory);
//        }
//        logger.info("increased the count for websocket in host table");
//    }

//    public void detachAgent(String uuid) {
//
//        logger.info("detachAgent: uuid: " + uuid);
//
//        if(uuid == null) {
//            logger.error("detachAgent: uuid is null");
//            return;
//        }
//
//        if(WebSocketHandler.uuidToAgent.containsKey(uuid)) {
//
//            String agent = WebSocketHandler.uuidToAgent.get(uuid);
//
//            if(WebSocketHandler.removeAgentSocket(uuid) == 0) {
//                try {
//
//                    DataReceiveService.unRegisterAgent(agent);
//                } catch (Exception e) {
//                    logger.error("couldn't un-register agent for rabbit mq: " + agent);
//                }
//            }
//
//            String hostName = machine.getName();
//            String host = hostName + agentMgrInternalConfiguration.getHostExtension();
//            //host = "172.20.227.25";
//            logger.info("This Host: " + host);
//
//            //remove from the DB
//            removeAgentFromDB(agent, host, uuid);
//            //decrement the count
//            decreaseSocketCount(host);
//        }
//    }
//
//
//    public void attachAgent(String agent, String clientID, AtmosphereResource r) {
//
//        String uuid = r.uuid();
//        logger.info("attachAgent: Attaching the agent: " + agent + " uuid: " + uuid);
//        logger.info("attachAgent: Client-id: " + clientID);
//
//        String hostName = machine.getName();
//        String host = hostName + agentMgrInternalConfiguration.getHostExtension();
//        //host = "172.20.227.25";
//        logger.info("This Host: " + host);
//
//        WebSocketHandler.addAgentSocket(agent, r);
//
//        try {
//            DataReceiveService.registerAgent(agent);
//        } catch (Exception e) {
//            logger.error("couldn't register agent for rabbit mq: " + agent + e.getMessage(),e);
//        }
//
//        //Add into the main DB
//        if(addAgentInDB(agent, host, uuid, clientID))
//            increaseSocketCount(host);
//    }


//    public Response validate(ClientMessage message) {
//
//        ImmutableList<String> violations = null;
//        violations = validator.validate(message);
//        if(violations != null) {
//            if (violations.size() > 0) {
//                ArrayList<String> validationMessages = new ArrayList<String>();
//                for (String violation : violations) {
//                    validationMessages.add(violation);
//                }
//                return Response
//                        .status(Response.Status.BAD_REQUEST)
//                        .entity(validationMessages)
//                        .build();
//            }
//        }
//        return Response.ok().build();
//    }

//    private String formSocketMessage(ClientMessage message) {
//
//        String msg = null;
//        SocketMessage socketMessage = new SocketMessage();
//
//        Channel channel = new Channel();
//        channel.setName(ChannelType.FLIPKART.name());
//
//        Event event = new Event();
//        event.setName(message.getType());
//        event.setId(message.getCallID());
//
//        //com.flipkart.agentMgrInternal.models.webSocket.Context context = new com.flipkart.agentMgrInternal.models.webSocket.Context();
//        //context.setUserComment(message.getData());
//
//        String context = message.getData();
//        AgentInfo agentInfo = new AgentInfo();
//        agentInfo.setFromId(message.getSender());
//        agentInfo.setId(message.getReceiver());
//
//        socketMessage.setAgentInfo(agentInfo);
//        socketMessage.setContext(context);
//        socketMessage.setChannel(channel);
//        socketMessage.setEvent(event);
//
//        ObjectMapper socketMapper = new ObjectMapper();
//        try {
//            msg = socketMapper.writeValueAsString(socketMessage);
//            logger.info("Message to be pushed: " + msg);
//        } catch (Exception e) {
//            logger.error("Exception while serializing the message for socket: " + e.getMessage(),e);
//        }
//        return msg;
//    }

//    private void saveMessageIntoDB(final ClientMessage message) {
//
//        Runnable task = new Runnable() {
//            @Override
//            public void run() {
//                Session session = null;
//                try {
//                    session = sessionFactory.openSession();
//
//                    ManagedSessionContext.bind(session);
//                    session.beginTransaction();
//
//                    String type = message.getType();
//                    String id = message.getId();
//                    String receiver = message.getReceiver();
//                    String sender = message.getSender();
//                    String msg = message.getData();
//                    String callID = null;
//                    ClientMessageType clientMessageType = null;
//
//                    if(type.equals(ClientMessageType.TRANSFER.name())) {
//
//                        callID = message.getCallID();
//                        clientMessageType = ClientMessageType.TRANSFER;
//
//                    } else if(type.equals(ClientMessageType.CHAT.name())) {
//                        clientMessageType = ClientMessageType.CHAT;
//                    }
//
//                    //saving into DB
//                    SocketMessageData socketMessageData = new SocketMessageData(sender,receiver,clientMessageType,msg,callID,id);
//                    socketMessageDao.save(socketMessageData);
//
//                    final Transaction txn = session.getTransaction();
//                    if (txn != null && txn.isActive()) {
//                        txn.commit();
//                    }
//                } catch (Exception e) {
//
//                    logger.error("Error while saving into DB ", e.getMessage(),e);
//                } finally {
//                    if(session != null) {
//                        session.close();
//                        ManagedSessionContext.unbind(sessionFactory);
//                    }
//                }
//            }
//        };
//
//        executorMgrService.executeTask(task);
//    }
}