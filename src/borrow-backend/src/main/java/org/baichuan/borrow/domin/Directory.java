package org.baichuan.borrow.domin;

import lombok.Data;
import org.apache.ibatis.annotations.Delete;

@Data
public class Directory {
    private String directory;
    private String directid;
    private String libid;
    private String num;
}
