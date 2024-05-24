package com.bbr.net;

import com.google.gwt.i18n.server.Message;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface NetAsync {
    void getMessage(String input, AsyncCallback<Message> callback);
}
