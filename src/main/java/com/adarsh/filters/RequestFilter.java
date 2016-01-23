package com.adarsh.filters;

import com.adarsh.utils.GuiceInjector;
import com.adarsh.utils.Machine;
import com.google.inject.Inject;
import org.glassfish.jersey.message.internal.ReaderWriter;
import org.glassfish.jersey.server.ContainerException;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
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

    private void setUidForRequest(ContainerRequestContext containerRequestContext) {

        String transId = containerRequestContext.getHeaders().getFirst("TransactionId");

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-HH-mm-ss-SSS");
        Calendar cal = Calendar.getInstance();
        String uId = dateFormat.format(cal.getTime()) + "-" + String.valueOf(UUID.randomUUID().getLeastSignificantBits()).substring(1, 3);

        if (transId != null) uId = transId + "-" + uId;
        String machineName = machine.getName();
        if (machineName != null) uId = machineName + "-" + uId;

        org.slf4j.MDC.put("id", uId);
        logger.info("TransactionId Generated : " + uId);
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        setUidForRequest(containerRequestContext);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = containerRequestContext.getEntityStream();;

        try {
            ReaderWriter.writeTo(in, out);

            byte[] requestEntity = out.toByteArray();
            logger.info("{} | {} | Request body: {}", containerRequestContext.getMethod(), containerRequestContext.getUriInfo(), out);

            MultivaluedMap<String, String> requestHeaders = containerRequestContext.getHeaders();
            logger.debug("Request Headers: ");
            for(MultivaluedMap.Entry entry : requestHeaders.entrySet()) {
                logger.debug("key: " + entry.getKey() + " " + "value: " + entry.getValue());
            }

            containerRequestContext.setEntityStream(new ByteArrayInputStream(requestEntity));
            logger.info("Filtering the request is done");
            //}
        } catch (IOException ex) {
            throw new ContainerException(ex);
        }
    }
}
