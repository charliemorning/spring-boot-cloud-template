package org.charlie.template.service;

import org.charlie.template.bo.CorpusBO;

public interface CorpusService {

    CorpusBO getCorpus(int id);

    void removeCorpus(int id);

    void modifyCorpus(CorpusBO corpusBO);


}
