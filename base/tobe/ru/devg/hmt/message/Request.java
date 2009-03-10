package ru.devg.hmt.message;

/**
 * @author Devgru &lt;devgru@mail.ru&gt;
 * @version 0.0
 * @deprecated 
 */
public abstract class Request {
    private Response response;

    public final void setResponse(Response response) {
        if (this.response == null) {
            this.response = response;
        }
    }

    public final Response getResponse() {
        return response;
    }

    public final boolean hasResponse() {
        return response != null;
    }
}
