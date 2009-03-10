package ru.devg.hmt.handler;

import ru.devg.hmt.message.Request;
import ru.devg.hmt.message.Response;
import ru.devg.hmt.tunnel.exceptions.NoResponseCanBeGiven;

/**
 * @author Devgru &lt;devgru@mail.ru&gt;
 * @version 0.0
 * @deprecated
 */
public interface Responder {
    Response respond(Request request) throws NoResponseCanBeGiven;

}

