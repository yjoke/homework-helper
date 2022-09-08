package work.yjoker.homeworkhelper.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import work.yjoker.homeworkhelper.util.Holder;

import java.time.LocalDateTime;

import static work.yjoker.homeworkhelper.util.Holder.ID_HOLDER;

/**
 * @author HeYunjia
 */

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("gmtCreate", LocalDateTime.now());
        metaObject.setValue("gmtModify", LocalDateTime.now());
        metaObject.setValue("idModify", Holder.get(ID_HOLDER));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("gmtModify", LocalDateTime.now());
        metaObject.setValue("idModify", Holder.get(ID_HOLDER));
    }
}
