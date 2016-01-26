package com.adp.filters;

import com.adp.utils.GuiceInjector;
import com.adp.utils.Machine;
import com.google.inject.Inject;
import com.sun.jersey.api.container.ContainerException;
import com.sun.jersey.core.util.ReaderWriter;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MultivaluedMap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by adarsh.sharma on 23/01/16.
 */
public class RequestFilter  implements ContainerRequestFilter {

    @Inject
    Machine machine;


    public RequestFilter() {
        GuiceInjector.getInjector().injectMembers(this);
    }

    final private static org.slf4j.Logger logger = LoggerFactory.getLogger(ContainerRequestFilter.class);

    private void setUidForRequest(ContainerRequest containerRequest) {

        String transId = containerRequest.getHeaderValue("TransactionId");

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-HH-mm-ss-SSS");
        Calendar cal = Calendar.getInstance();
        String uId = dateFormat.format(cal.getTime()) + "-" + String.valueOf(UUID.randomUUID().getLeastSignificantBits()).substring(1, 3);

        if (transId != null) uId = transId + "-" + uId;
        String machineName = machine.getName();
        if (machineName != null) uId = machineName + "-" + uId;

        org.slf4j.MDC.put("id", uId);
        logger.info("TransactionId Generated : " + uId);



    }

    public ContainerRequest filter(ContainerRequest containerRequest) {
        setUidForRequest(containerRequest);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = containerRequest.getEntityInputStream();
        try {
            //if (in.available() > 0) {
            ReaderWriter.writeTo(in, out);

            byte[] requestEntity = out.toByteArray();
            logger.info("{} | {} | Request body: {}", containerRequest.getMethod(), containerRequest.getRequestUri(), out);

            MultivaluedMap<String, String> requestHeaders = containerRequest.getRequestHeaders();
            logger.info("Request Headers: ");
            for(MultivaluedMap.Entry entry : requestHeaders.entrySet()) {
                logger.info("key: " + entry.getKey() + " " + "value: " + entry.getValue());
            }

            containerRequest.setEntityInputStream(new ByteArrayInputStream(requestEntity));
            logger.info("Filtering the request is done");
            //}
            return containerRequest;
        } catch (IOException ex) {
            throw new ContainerException(ex);
        }
    }
}


//public class RequestFilter  implements ContainerRequestFilter {
//
//    @Inject
//    Machine machine;
//
//    public RequestFilter() {
//        GuiceInjector.getInjector().injectMembers(this);
//    }
//
//    final private static org.slf4j.Logger logger = LoggerFactory.getLogger(ContainerRequestFilter.class);
//
//    private void setUidForRequest(ContainerRequestContext containerRequestContext) {
//
//        String transId = containerRequestContext.getHeaders().getFirst("TransactionId");
//
//        DateFormat dateFormat = new SimpleDateFormat("MM-dd-HH-mm-ss-SSS");
//        Calendar cal = Calendar.getInstance();
//        String uId = dateFormat.format(cal.getTime()) + "-" + String.valueOf(UUID.randomUUID().getLeastSignificantBits()).substring(1, 3);
//
//        if (transId != null) uId = transId + "-" + uId;
//        String machineName = machine.getName();
//        if (machineName != null) uId = machineName + "-" + uId;
//
//        org.slf4j.MDC.put("id", uId);
//        logger.info("TransactionId Generated : " + uId);
//    }
//
//    @Override
//    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
//        setUidForRequest(containerRequestContext);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        InputStream in = containerRequestContext.getEntityStream();;
//
//        try {
//            ReaderWriter.writeTo(in, out);
//
//            byte[] requestEntity = out.toByteArray();
//            logger.info("{} | {} | Request body: {}", containerRequestContext.getMethod(), containerRequestContext.getUriInfo(), out);
//
//            MultivaluedMap<String, String> requestHeaders = containerRequestContext.getHeaders();
//            logger.debug("Request Headers: ");
//            for(MultivaluedMap.Entry entry : requestHeaders.entrySet()) {
//                logger.debug("key: " + entry.getKey() + " " + "value: " + entry.getValue());
//            }
//
//            containerRequestContext.setEntityStream(new ByteArrayInputStream(requestEntity));
//            logger.info("Filtering the request is done");
//            //}
//        } catch (IOException ex) {
//            throw new ContainerException(ex);
//        }
//    }
//}
