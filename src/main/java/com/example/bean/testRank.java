package com.example.bean;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;

import java.util.*;

public class testRank {
    private static final int keyWordNum=3;
    private static final float d=0.85f;
    private static final int maxIter=200;
    private static final float minDiff=0.001f;

    public testRank() {
    }

    public String getKeyWords(String title,String content){
        // 分词
//        List<Term> termList = HanLP.segment(title + content);
//        System.out.println(termList);
//        System.out.println("-------------------------------");
        List<Word> or_wordList = WordSegmenter.seg(title+content);
        List<String> wordList=new ArrayList<>();
        for (Word t : or_wordList)
        {
            String temp_word=t.toString();
            wordList.add(temp_word);
        }
        //System.out.println(wordList);
        Map<String, Set<String>> words = new HashMap<String, Set<String>>();
        Queue<String> que = new LinkedList<String>();
        for (String w : wordList)
        {
            if (!words.containsKey(w))
            {
                words.put(w, new HashSet<String>());
            }
            que.offer(w);
            if (que.size() > 5)
            {
                que.poll();
            }

            for (String w1 : que)
            {
                for (String w2 : que)
                {
                    if (w1.equals(w2))
                    {
                        continue;
                    }

                    words.get(w1).add(w2);
                    words.get(w2).add(w1);
                }
            }
        }
//        System.out.println(words);
        Map<String, Float> score = new HashMap<String, Float>();
        for (int i = 0; i < maxIter; ++i)
        {
            Map<String, Float> m = new HashMap<String, Float>();
            float max_diff = 0;
            for (Map.Entry<String, Set<String>> entry : words.entrySet())
            {
                String key = entry.getKey();
                Set<String> value = entry.getValue();
                m.put(key, 1 - d);
                for (String other : value)
                {
                    int size = words.get(other).size();
                    if (key.equals(other) || size == 0) continue;
                    m.put(key, m.get(key) + d / size * (score.get(other) == null ? 0 : score.get(other)));
                }
                max_diff = Math.max(max_diff, Math.abs(m.get(key) - (score.get(key) == null ? 0 : score.get(key))));
            }
            score = m;
            if (max_diff <= minDiff) break;
        }
        List<Map.Entry<String, Float>> entryList = new ArrayList<Map.Entry<String, Float>>(score.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Float>>()
        {
            @Override
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2)
            {
                return (o1.getValue() - o2.getValue() > 0 ? -1 : 1);
            }
        });
        //System.out.println(entryList);

        //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        String result = "";
        for (int i = 0; i < keyWordNum; ++i)
        {
            result += entryList.get(i).getKey() + '#';
        }
        return result;

    }

    public boolean shouldInclude(Term term)
    {
        return CoreStopWordDictionary.shouldInclude(term);
    }

    public static void main(String[] args) {
        String s="           最近，网上有文章称，儿童睡前不能喝牛奶，这样不仅影响补钙，而且会使肠胃虚弱。文章还列举了一个例子：9岁的西西身高只有108厘米，医生说是缺钙造成的。原来，孩子这3年都是睡前喝牛奶，导致肠胃得不到休息，造成了胃肠虚弱。医生建议要在孩子睡前一个小时左右给孩子喝牛奶，这样才能有充分的时间来消化和吸收其中的钙质。\n" +
                "\n" +
                "这是真的吗？\n" +
                "\n" +
                "一、这个案例很可能是编造的\n" +
                "\n" +
                "这很大可能是一个编造的案例。流传文章提到男孩身高只有108厘米，这大约是4岁半男孩的平均身高，而9岁男孩的身高标准是124.6厘米，明显差距过大。儿童身材矮小可能与很多因素有关，如果不排查其他病理原因，而让缺钙来“背锅”，这显然是缺乏依据的。\n" +
                "\n" +
                "二、我们在睡觉的时候，肠胃也没有停工\n" +
                "\n" +
                "钙在肠胃中的吸收有两种机制，一是通过十二指肠和近端空肠顶端膜上的一种叫做TRPV6的通道主动吸收；二是通过整个肠道的细胞旁路通道转运。在快速眼动睡眠（做梦的时候）胃的排空较快，在其他睡眠阶段胃排空较慢；小肠的蠕动速度虽然比白天慢，但是比白天更规律。换句话说，就是胃肠道在放慢工作节奏，但没有停工。要知道，鱼和肉这些高蛋白食物往往需要2天左右才能完全消化，如果睡眠的时候消化道停工，那影响吸收的可不只是睡前喝的奶，连中午吃的肉也会受到影响。\n" +
                "\n" +
                "三、谣言文章提到的孩子缺钙的表现也不对\n" +
                "\n" +
                "文章还提到孩子体内缺钙会有几种表现，比如食欲低、体质差、发育缓慢、睡眠不佳等。对于这些表现，通常也不会认为是缺钙造成的。孩子食欲低最常见的原因，你可能想不到，那就是“进食压力”，如果孩子吃得少，家长用威逼或者利诱来让孩子吃饭，可能引起孩子的反感而讨厌吃饭。所以，国外的喂养专家通常是心理学者，而不是消化科医生，例如SOS喂养法的创始人儿科心理学家Toomy KA博士，和STEP+喂养法的创始人Rowell博士。家长认为的体质差有时候是儿童期正常的反复呼吸道感染，要知道6岁以内的孩子一年平均感冒次数是6-8次。孩子如果真的是发育迟缓，可得看看小儿神经专科的医生评估原因，千万不要补钙了之。睡眠不佳的原因非常多，缺钙通常还不能“上榜”。\n" +
                "\n" +
                "钙确实很重要，但主要是针对骨骼健康而言。缺钙会降低骨骼强度并可能导致骨质疏松，也可能导致儿童佝偻病（佝偻病更常见是维生素D缺乏造成的，而不是缺钙）。\n" +
                "\n" +
                "四、如果真的缺钙，可以考虑补充钙剂\n" +
                "\n" +
                "流传文章中，医生建议儿童服用利用率和转化率极高的碳维氧钙，据说这个钙含量是牛奶的10倍，但我穷极我的搜索本领也没有查询到这个药物，恐怕又是营销号在写推广软文。如果我们从饮食中没有摄入足够的钙，也可以考虑补充钙剂。最常用的钙剂是碳酸钙（和食物一起服用吸收率更高）和柠檬酸钙（不依赖食物吸收）。其他钙类，例如葡萄糖酸钙、乳酸钙等也在临床中会用到。\n" +
                "        ";
        testRank ts=new testRank();
        String sa= ts.getKeyWords("",s);
        System.out.println(sa);
    }


}
