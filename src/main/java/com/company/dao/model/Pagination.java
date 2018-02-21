package com.company.dao.model;

import java.util.List;

public interface Pagination<T> {

    int countPages(int recordsOnPage);

    List<T> getPage(int page, int recordsOnPage);
}