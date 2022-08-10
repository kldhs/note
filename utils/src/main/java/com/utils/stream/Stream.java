package com.utils.stream;

import org.apache.kafka.common.protocol.types.Field;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xs
 * @date 2022/1/21 15:57
 */

/**
 * Java 8 API添加了一个新的抽象称为流Stream，可以让你以一种声明的方式处理数据。
 * Stream 使用一种类似用 SQL 语句从数据库查询数据的直观方式来提供一种对 Java 集合运算和表达的高阶抽象。
 * Stream将要处理的元素集合看作一种流， 流在管道中传输， 并且可以在管道的节点上进行处理， 比如筛选， 排序，聚合等。
 * 元素流在管道中经过中间操作（intermediate operation）的处理，最后由最终操作(terminal operation)得到前面处理的结果
 */


public class Stream {
    private String name;
    private int age;
    private String gender;
    private int height;
    private int weight;

    public Stream(String name, int age, String gender, int height, int weight) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Stream{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }

    public static void main(String args[]) {
        ArrayList<Stream> streams = new ArrayList<>();
        streams.add(new Stream("小张", 12, "男", 151, 40));
        streams.add(new Stream("小红", 12, "女", 152, 40));
        List<String> strings = Arrays.asList("1234", "", "3432", "0743", "524", "", "785");
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        System.out.println("streams " + streams);

        String collect0 = streams.stream().map(p -> p.getName()).collect(Collectors.joining(","));
        System.out.println("collect : " + collect0);

        streams.stream().sorted(Comparator.comparing(Stream::getAge)).collect(Collectors.groupingBy(Stream::getGender));


        new Random().ints().limit(10).sorted().forEach(p -> {
            System.out.println("随机数 ：" + p);
        });


        String collect1 = streams.stream().map(Objects::toString).collect(Collectors.joining(","));
        System.out.println("collect1 : " + collect1);

        int[] ints = strings.stream().mapToInt(p -> {
            if (p.equals("")) {
                p = "0";
            }
            return Integer.parseInt(p);
        }).toArray();
        String collect2 = Arrays.stream(ints).mapToObj(Integer::toString).collect(Collectors.joining(","));
        System.out.println("collect2 : " + collect2);

        List<String> collect3 = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        System.out.println("collect3 : " + collect3);

        List<Integer> collect4 = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        System.out.println("collect4 : " + collect4.toString());

        long count = strings.stream().filter(string -> string.isEmpty()).count();
        System.out.println("count : " + count);

        long count1 = strings.stream().filter(string -> string.length() == 3).count();
        long count3 = strings.stream().filter(string -> {
            if (string.length() == 3) {
                return false;
            } else {
                return true;
            }
        }).count();
        System.out.println("count1 : " + count1);
        System.out.println("count3 : " + count3);

        // 并行处理
        long count2 = strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println("count2 : " + count2);

        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());

        new Random().ints().limit(10).sorted().forEach(p -> {
            System.out.println("随机数 ：" + p);
        });
    }


}

