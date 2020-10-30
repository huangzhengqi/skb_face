package com.fzy.admin.fp.distribution.article.vo;

import com.fzy.admin.fp.distribution.article.domain.SecondSection;
import com.fzy.admin.fp.distribution.article.domain.ThirdSection;
import lombok.Data;

import java.util.List;

/**
 * @Author yy
 * @Date 2020-2-10 17:50:48
 * @Desp
 **/
@Data
public class SecondSectionVO {
    private SecondSection secondSection;

    private List<ThirdSection> thirdSection;

}
