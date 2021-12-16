package tw.edu.ntub.imd.plearnet.service.transformer.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.plearnet.bean.MessageBean;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Message;
import tw.edu.ntub.imd.plearnet.service.transformer.MessageTransformer;

@Component
public class MessageTransformerImpl implements MessageTransformer {

    @NonNull
    @Override
    public Message transferToEntity(@NonNull MessageBean messageBean){return JavaBeanUtils.copy(messageBean, new Message()); }

    @NonNull
    @Override
    public MessageBean transferToBean(@NonNull Message message){return JavaBeanUtils.copy(message, new MessageBean());}
}
