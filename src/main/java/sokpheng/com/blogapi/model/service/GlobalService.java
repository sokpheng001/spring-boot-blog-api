package sokpheng.com.blogapi.model.service;


import org.springframework.data.domain.Page;

import java.util.List;

public interface GlobalService<T,P> {
    Page<T> getAllDataByPagination(int pageNumber, int pageSize);
    List<T> getAll();
    T getByUuid(String uuid);
    int deleteByUuid(String uuid);
    T create(P o);
}
