package util;

import attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.domain.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dingzhaolei
 * @date 2018/12/23 16:17
 **/
public class SessionUtil {
    private static final Logger logger = LoggerFactory.getLogger(SessionUtil.class);
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();
    private static final Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();
    private static final Map<String, List<Session>> groupIdMembersMap = new ConcurrentHashMap<>();


    public static void bindSession(Session session, Channel channel){
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel){
        if (hasLogin(channel)){
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }


    public static boolean hasLogin(Channel channel){
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel){
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId){
        return userIdChannelMap.get(userId);
    }

    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup){
        groupIdChannelGroupMap.put(groupId,channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId){
        return groupIdChannelGroupMap.get(groupId);
    }

    public static void addMembers(String groupId, List<Session> list){
        List<Session> temp = groupIdMembersMap.get(groupId);
        if (temp == null) {
            temp = new ArrayList<>();
        }
        temp.addAll(list);

        groupIdMembersMap.put(groupId, temp);
        logger.info("添加后groupId:{},成员:{}",groupId, temp);
    }

    public static void addMembers(String groupId, Session session){
        List<Session> temp = groupIdMembersMap.get(groupId);
        if (temp == null) {
            temp = new ArrayList<>();
        }
        temp.add(session);

        groupIdMembersMap.put(groupId, temp);
        logger.info("添加后groupId:{},成员:{}",groupId, temp);
    }

    public static void removeMembers(String groupId, List<Session> list){
        List<Session> temp = groupIdMembersMap.get(groupId);

        if (temp == null){
            logger.info("删除失败没有该成员");
        }
        temp.removeAll(list);
        groupIdMembersMap.put(groupId, temp);
        logger.info("删除后groupId:{},成员:{}",groupId, temp);
    }

    public static void removeMembers(String groupId, Session session){
        List<Session> temp = groupIdMembersMap.get(groupId);
        if (temp == null){
            logger.info("删除失败没有该成员");
            return;
        }

        temp.remove(session);
        groupIdMembersMap.put(groupId, temp);
        logger.info("删除后groupId:{},成员:{}",groupId, temp);
    }

    public static List<Session> listMembers(String groupId){
        return groupIdMembersMap.get(groupId);
    }



}
