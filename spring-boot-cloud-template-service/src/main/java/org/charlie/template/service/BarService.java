package org.charlie.template.service;

import org.charlie.template.bo.BarBO;

import java.util.List;

public interface BarService {
    List<BarBO> queryBars(BarBO barBO);
    void addBar(BarBO barBO);
    void modifyBar(BarBO barBO);
    void removeBar(BarBO barBO);
}
