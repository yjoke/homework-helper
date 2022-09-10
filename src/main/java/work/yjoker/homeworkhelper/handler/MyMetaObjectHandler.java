package work.yjoker.homeworkhelper.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import work.yjoker.homeworkhelper.util.Holder;

import java.util.Date;

import static work.yjoker.homeworkhelper.util.Holder.ID_HOLDER;
import static work.yjoker.homeworkhelper.util.Holder.NULL_VALUE;

/**
 * @author HeYunjia
 */

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        metaObject.setValue("gmtCreate", date);
        metaObject.setValue("gmtModify", date);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("gmtModify", new Date());
        String idModify = Holder.get(ID_HOLDER);
        if (NULL_VALUE.equals(idModify)) idModify = String.valueOf(metaObject.getValue("id"));
        metaObject.setValue("idModify", Long.valueOf(idModify));
    }
}
