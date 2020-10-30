package com.fzy.admin.fp.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Created by zk on 2019-05-01 14:16
 * @description 用于曲线图数据展示
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartDataVO {
    private List<String> num;
    private List<String> xData;
    private List<String> yData;
}
