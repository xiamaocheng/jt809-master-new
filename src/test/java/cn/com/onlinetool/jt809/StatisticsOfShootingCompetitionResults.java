package cn.com.onlinetool.jt809;

/**
 * @author choice
 * @description:
 * @date 2019-06-28 10:34
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static java.lang.System.in;

/**
 * 统计射击比赛成绩
 * 13
 * 3,3,7,4,4,4,4,7,7,3,5,5,5
 * 53,80,68,24,39,76,66,16,100,55,53,80,55
 */
public class StatisticsOfShootingCompetitionResults {
    public static void main(String[] args) throws IOException {
        // 收集数据源
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        int n = parseInt(bf.readLine()); // N次射击
        List<Integer> ids = Arrays.stream(bf.readLine().split(",")).limit(n).map(Integer::parseInt).collect(Collectors.toList());// userId
        List<Integer> scores = Arrays.stream(bf.readLine().split(",")).limit(n).map(Integer::parseInt).collect(Collectors.toList());// 得分
        bf.close();

        // 收集成绩信息
        Map<Integer, List<Integer>> res = new HashMap();
        for (int i = 0; i < n; i++) {
            Integer id = ids.get(i);
            Integer score = scores.get(i);
            List<Integer> orDefault = res.getOrDefault(id, new LinkedList<>());
            orDefault.add(score);
            res.put(id, orDefault);
        }

        String collect = res.entrySet().stream()
                .filter(per -> (per.getValue().size() >= 3)) // 成绩少于3个成绩无效
                .sorted((p1, p2) -> {
                    // 成绩降序→取前三→求和
                    int sum1 = p1.getValue().stream().sorted(Comparator.reverseOrder()).limit(3).mapToInt(Integer::valueOf).sum();
                    int sum2 = p2.getValue().stream().sorted(Comparator.reverseOrder()).limit(3).mapToInt(Integer::valueOf).sum();
                    // 最大成绩降序前三加和降序，加和相同的话Id降序
                    if (sum1 == sum2) {
                        return p2.getKey() - p1.getKey();
                    } else {
                        return sum2 - sum1;
                    }
                })
                .map(Map.Entry::getKey) // 取得员工Id
                .map(String::valueOf) // 类型不转没办法joining
                .collect(Collectors.joining(",")); // 拼接字符串

        // 输出结果
        System.out.println(collect);
    }
}
