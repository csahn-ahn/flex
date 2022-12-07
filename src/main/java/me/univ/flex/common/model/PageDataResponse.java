package me.univ.flex.common.model;

import lombok.Data;

@Data
public class PageDataResponse<T> {
    long totalCount;
    T payload;
}
