package com.test.controller;

import com.test.entity.User;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class TestListToMap {

    public static void main(String[] args) {
        List<User> longs = new ArrayList<>();
        User user = new User("a","1");
        longs.add(user);
        User user1 = new User("b","2");
        longs.add(user1);
        User user2 = new User("c","3");
        longs.add(user2);
        String join = StringUtils.join(longs, ",");
        System.out.println(join);

        Map<String, User> collect = longs.stream().collect(Collectors.toMap(User::getEmpId, a -> a));
        System.out.println(collect);

        HashMap<String, Object> collect1 = longs.stream().collect(HashMap::new, (m, v) -> m.put(v.getEmpId(), v.getName()), HashMap::putAll);
        System.out.println(collect1);






    }
}
