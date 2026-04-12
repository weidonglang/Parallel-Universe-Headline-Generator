package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class UniverseNewsService {

    private static final List<String> OPENERS = List.of(
            "震惊但合理",
            "刚刚确认",
            "内部人士透露",
            "全网围观中",
            "今天最离谱的好消息"
    );

    private static final List<String> DEFAULT_TAGS = List.of(
            "平行宇宙特供",
            "今天也很会整活",
            "轻度神话",
            "离谱但真香",
            "戏剧性升级"
    );

    private static final TemplateSet PERSON_SET = new TemplateSet(
            List.of(
                    "%s在普通日常里突然完成风格升级，被临时推举为%s",
                    "%s一句话扭转现场气氛，现已兼任%s",
                    "%s把随手操作做成招牌动作，正式成为%s"
            ),
            List.of(
                    "%s出现后，围观群众的表情从疑惑逐步过渡到佩服。",
                    "知情人表示，%s原本只是正常发挥，但现场已经自发写起了传奇版本。",
                    "%s的操作过于流畅，导致旁观者一度怀疑是不是提前彩排过。"
            ),
            List.of(
                    "“我只是按自己的节奏来，结果节奏自己变成了主角。”",
                    "“他们以为我在即兴，其实我是和气氛达成了合作。”",
                    "“这不是超常发挥，这是我和今天的默契。”"
            ),
            List.of(
                    "建议你把%s今天的名场面记下来，未来很可能会升格为部门传说。",
                    "现在适合给%s补一个新头衔，至少正式使用十分钟。",
                    "推荐做法：见到%s时保持尊重，并适度学习这种稳定输出的神态。"
            ),
            List.of("传奇同事", "气氛掌控者", "临场发挥大师", "摸鱼艺术家", "社交节奏师"),
            List.of("人物观察", "社交天赋觉醒", "办公室传说", "今天状态过强", "主角感溢出")
    );

    private static final TemplateSet ANIMAL_SET = new TemplateSet(
            List.of(
                    "%s靠天然气场接管现场，荣升为%s",
                    "%s用一个动作收买全场，已被认证为%s",
                    "%s没有开口，但气氛已经默认它是%s"
            ),
            List.of(
                    "现场画面显示，%s只是正常活动，周围人类却已经开始自动配音。",
                    "目击者称，%s带来的压迫感和可爱感同时在线，难以反抗。",
                    "相关人员承认，%s一出场，原计划基本就失去解释权了。"
            ),
            List.of(
                    "“它什么都没说，但所有安排都像是它批准过的。”",
                    "“这不是卖萌，这是成熟的现场调度能力。”",
                    "“它只是路过一下，结果大家都开始想给它颁奖。”"
            ),
            List.of(
                    "建议立刻给%s准备一份虚构履历，它显然配得上高级职位。",
                    "今日任务：认真夸%s三句，避免错过建立友好关系的窗口期。",
                    "推荐操作：把%s的高光时刻记录下来，留作低落时段的精神补给。"
            ),
            List.of("可爱秩序官", "现场总指挥", "撒娇战略家", "毛茸茸主管", "无声统治者"),
            List.of("宠物新闻", "毛茸茸权威", "可爱压制", "现场已沦陷", "全员自愿服从")
    );

    private static final TemplateSet EVENT_SET = new TemplateSet(
            List.of(
                    "%s突然偏离原计划，现场被迫进入%s模式",
                    "%s完成戏剧性升级，成为今日最强%s",
                    "%s本来只是流程的一部分，后来变成了%s"
            ),
            List.of(
                    "根据最新观察，%s已经从普通安排演化成需要重点围观的事件。",
                    "相关人员回忆，%s原本没有人抱期待，但它自己把剧情撑起来了。",
                    "消息传开后，%s被一致评价为“比原定版本更有看头”。"
            ),
            List.of(
                    "“没人预判到它会这么发展，但大家都承认这走向不错。”",
                    "“一开始只想顺利结束，后来发现它更适合被当成名场面。”",
                    "“这件事最厉害的地方，是它自己长出了第二幕。”"
            ),
            List.of(
                    "建议给%s做一次正式复盘，重点分析它为什么能突然好看起来。",
                    "下一步很简单：对%s降低控制欲，让它自由发挥一会儿。",
                    "推荐把%s写进今日总结，不然这段剧情会显得像编的。"
            ),
            List.of("节奏突变源", "剧情发动机", "流程改造者", "现场焦点", "今日名场面"),
            List.of("事件升级", "流程失控但好看", "会议文学", "剧情突然成立", "围观价值很高")
    );

    private static final TemplateSet OBJECT_SET = new TemplateSet(
            List.of(
                    "%s状态异常在线，现已被尊称为%s",
                    "%s用超规格表现刷新存在感，晋升为%s",
                    "%s突然展现隐藏天赋，成为现场唯一的%s"
            ),
            List.of(
                    "目击者称，%s明明只是个物件，却做出了非常有立场的表现。",
                    "多方消息显示，%s的发挥已经超出工具定位，开始带点角色弧光。",
                    "围观人群一致认为，%s今天的状态不像被使用，更像主动出手。"
            ),
            List.of(
                    "“它今天不是在配合工作，它是在带节奏。”",
                    "“以前只把它当工具，现在得承认它有个人风格。”",
                    "“它没有情绪表达接口，但明显已经表达了态度。”"
            ),
            List.of(
                    "建议对%s保持基本敬意，至少不要在它面前说它可替代。",
                    "今日行动：给%s补一段英雄背景，解释它为什么突然这么能打。",
                    "推荐给%s举行一次象征性表彰，防止它以后骄傲得太明显。"
            ),
            List.of("工具界卷王", "沉默实力派", "机械系明星", "高效代言人", "功能型主角"),
            List.of("物件觉醒", "工具人反转", "存在感拉满", "机器也有尊严", "超出预期")
    );

    private static final TemplateSet ABSTRACT_SET = new TemplateSet(
            List.of(
                    "%s突然有了实体感，已被定义为%s",
                    "%s从抽象名词进化成现场共识，成为%s",
                    "%s不再只是概念，今天正式担任%s"
            ),
            List.of(
                    "观察显示，%s本来难以描述，但今天已经具体到可以被指认出来了。",
                    "围观者表示，%s的存在感持续升高，甚至比某些计划本身还清晰。",
                    "最新动态表明，%s已经完成抽象到具象的跨越。"
            ),
            List.of(
                    "“以前只会嘴上说它，今天终于看见它本人了。”",
                    "“这东西一旦落地，连空气都像被说服了。”",
                    "“概念不可怕，可怕的是它今天真的开始发光。”"
            ),
            List.of(
                    "建议你趁现在给%s下个定义，不然它会继续自由生长。",
                    "推荐把%s写进今日关键词，方便以后证明它确实来过。",
                    "现在最适合围绕%s展开十分钟讨论，假装自己很早就看懂了。"
            ),
            List.of("概念实体化专家", "抽象气氛源", "灵感本体", "意识流主角", "今日定义权"),
            List.of("概念落地", "灵感突然具象", "情绪价值拉满", "抽象但有效", "脑洞成真")
    );

    public UniverseNews generate(String input) {
        String anchor = normalize(input);
        Random random = new Random(anchor.hashCode());
        TopicType type = detectType(anchor);
        TemplateSet set = templateSetFor(type);
        String subject = naturalSubject(anchor, type);

        String headline = pick(random, OPENERS) + "：" + pick(random, set.headlines()).formatted(subject, pick(random, set.roles()));

        return new UniverseNews(
                anchor,
                headline,
                pick(random, set.scoops()).formatted(subject),
                pick(random, set.quotes()),
                pick(random, set.actions()).formatted(subject),
                uniqueTags(random, set.tags())
        );
    }

    private List<String> uniqueTags(Random random, List<String> primaryTags) {
        Set<String> tags = new LinkedHashSet<>();
        tags.add(pick(random, primaryTags));
        while (tags.size() < 3) {
            tags.add(pick(random, DEFAULT_TAGS));
        }
        return new ArrayList<>(tags);
    }

    private TopicType detectType(String anchor) {
        String value = anchor.toLowerCase();

        if (isGenericAnimal(value)) {
            return TopicType.ANIMAL;
        }
        if (isGenericEvent(value)) {
            return TopicType.EVENT;
        }
        if (isGenericObject(value)) {
            return TopicType.OBJECT;
        }
        if (containsAny(value, "猫", "狗", "兔", "仓鼠", "鹦鹉", "小橘", "小黑", "小白")) {
            return TopicType.ANIMAL;
        }
        if (containsAny(value, "会议", "早会", "周一", "周末", "上班", "下班", "地铁", "午休", "ddl", "deadline", "团建", "摸鱼", "早八")) {
            return TopicType.EVENT;
        }
        if (containsAny(value, "电脑", "手机", "键盘", "空调", "咖啡机", "杯子", "打印机", "风扇", "路由器", "耳机")) {
            return TopicType.OBJECT;
        }
        if (containsAny(value, "自由", "灵感", "运气", "幸福", "压力", "困意", "勇气", "爱情", "班味")) {
            return TopicType.ABSTRACT;
        }
        if (looksLikePerson(anchor)) {
            return TopicType.PERSON;
        }
        if (anchor.length() >= 4) {
            return TopicType.EVENT;
        }
        return TopicType.ABSTRACT;
    }

    private boolean isGenericAnimal(String value) {
        return containsAny(value, "宠物", "动物", "猫咪", "狗狗");
    }

    private boolean isGenericEvent(String value) {
        return containsAny(value, "会议", "活动", "仪式", "流程", "场面");
    }

    private boolean isGenericObject(String value) {
        return containsAny(value, "物件", "东西", "设备", "机器", "工具");
    }

    private TemplateSet templateSetFor(TopicType type) {
        return switch (type) {
            case PERSON -> PERSON_SET;
            case ANIMAL -> ANIMAL_SET;
            case EVENT -> EVENT_SET;
            case OBJECT -> OBJECT_SET;
            case ABSTRACT -> ABSTRACT_SET;
        };
    }

    private String naturalSubject(String anchor, TopicType type) {
        return switch (type) {
            case PERSON -> anchor;
            case ANIMAL -> naturalAnimal(anchor);
            case EVENT -> naturalEvent(anchor);
            case OBJECT -> naturalObject(anchor);
            case ABSTRACT -> naturalAbstract(anchor);
        };
    }

    private String naturalAnimal(String anchor) {
        if (containsAny(anchor, "宠物", "动物")) {
            return "这只" + anchor;
        }
        if (anchor.endsWith("猫") || anchor.endsWith("狗") || anchor.endsWith("兔")) {
            return anchor.startsWith("这只") ? anchor : "这只" + anchor;
        }
        return anchor;
    }

    private String naturalEvent(String anchor) {
        if (containsAny(anchor, "会议", "活动", "仪式")) {
            return anchor.startsWith("这场") ? anchor : "这场" + anchor;
        }
        if (anchor.endsWith("早会") || anchor.endsWith("团建") || anchor.endsWith("午休")) {
            return anchor.startsWith("这次") ? anchor : "这次" + anchor;
        }
        return anchor;
    }

    private String naturalObject(String anchor) {
        if (containsAny(anchor, "物件", "东西", "设备", "工具")) {
            return "这个" + anchor;
        }
        if (anchor.endsWith("机") || anchor.endsWith("器")) {
            return anchor.startsWith("这台") ? anchor : "这台" + anchor;
        }
        if (anchor.endsWith("杯") || anchor.endsWith("杯子") || anchor.endsWith("键盘")) {
            return anchor.startsWith("这个") ? anchor : "这个" + anchor;
        }
        return anchor;
    }

    private String naturalAbstract(String anchor) {
        if (anchor.length() <= 2) {
            return "这份" + anchor;
        }
        return anchor;
    }

    private boolean looksLikePerson(String anchor) {
        return anchor.length() <= 3
                && !isGenericAnimal(anchor)
                && !isGenericEvent(anchor)
                && !isGenericObject(anchor)
                || containsAny(anchor, "老", "小", "阿", "哥", "姐", "总", "老师", "同学", "经理", "主管");
    }

    private boolean containsAny(String text, String... keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private String normalize(String input) {
        if (input == null || input.isBlank()) {
            return "今天的你";
        }
        return input.trim();
    }

    private String pick(Random random, List<String> items) {
        return items.get(random.nextInt(items.size()));
    }

    private enum TopicType {
        PERSON, ANIMAL, EVENT, OBJECT, ABSTRACT
    }

    private record TemplateSet(
            List<String> headlines,
            List<String> scoops,
            List<String> quotes,
            List<String> actions,
            List<String> roles,
            List<String> tags
    ) {
    }
}
