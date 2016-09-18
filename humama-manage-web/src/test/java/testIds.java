import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/18 .
 * @Version: 1.0 .
 */
public class testIds {

    @Test
    public void ids(){
        String[] args = StringUtils.split("123,456,23123,213123,233,333,,",",");
        List<String> list = Arrays.asList(args);
        System.out.println(list.size());
        for (String s : list){
            System.out.println(s);
        }
    }
}
