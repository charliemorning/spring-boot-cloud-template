package org.charlie.example.service.api;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper;
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface CacheService<T> extends IService<T> {

    @Override
    default boolean saveOrUpdateBatch(Collection<T> entityList) {
        return false;
    }

    @Override
    boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);


    @Caching(
            evict = {
                    @CacheEvict(key = "id", beforeInvocation = true),
                    @CacheEvict(key = "id")
            })
    boolean removeById(Serializable id);

    @Caching(
            evict = {
                    @CacheEvict(key = "id", beforeInvocation = true),
                    @CacheEvict(key = "id")
            })
    boolean removeById(Serializable id, boolean useFill);

    @Caching(
            evict = {
                    @CacheEvict(key = "#entity.id", beforeInvocation = true),
                    @CacheEvict(key = "#entity.id")
            })
    default boolean removeById(T entity) {
        return false;
    }

    @Override
    default boolean removeByMap(Map<String, Object> columnMap) {
        return false;
    }

    @Override
    default boolean remove(Wrapper<T> queryWrapper) {
        return false;
    }

    @Override
    default boolean updateById(T entity) {
        return false;
    }

    @Override
    default boolean update(Wrapper<T> updateWrapper) {
        return false;
    }

    @Override
    default boolean update(T entity, Wrapper<T> updateWrapper) {
        return false;
    }

    @Override
    default boolean updateBatchById(Collection<T> entityList) {
        return false;
    }

    @Override
    boolean updateBatchById(Collection<T> entityList, int batchSize);

    @Override
    boolean saveOrUpdate(T entity);

    @Override
    default T getById(Serializable id) {
        return null;
    }

    @Override
    default List<T> listByIds(Collection<? extends Serializable> idList) {
        return null;
    }

    @Override
    default List<T> listByMap(Map<String, Object> columnMap) {
        return null;
    }

    @Override
    default T getOne(Wrapper<T> queryWrapper) {
        return null;
    }

    @Override
    T getOne(Wrapper<T> queryWrapper, boolean throwEx);

    @Override
    Map<String, Object> getMap(Wrapper<T> queryWrapper);

    @Override
    <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);

    @Override
    default long count() {
        return 0;
    }

    @Override
    default long count(Wrapper<T> queryWrapper) {
        return 0;
    }

    @Override
    default List<T> list(Wrapper<T> queryWrapper) {
        return null;
    }

    @Override
    default List<T> list() {
        return null;
    }

    @Override
    default <E extends IPage<T>> E page(E page, Wrapper<T> queryWrapper) {
        return null;
    }

    @Override
    default <E extends IPage<T>> E page(E page) {
        return null;
    }

    @Override
    default List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper) {
        return null;
    }

    @Override
    default List<Map<String, Object>> listMaps() {
        return null;
    }

    @Override
    default List<Object> listObjs() {
        return null;
    }

    @Override
    default <V> List<V> listObjs(Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    default List<Object> listObjs(Wrapper<T> queryWrapper) {
        return null;
    }

    @Override
    default <V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    default <E extends IPage<Map<String, Object>>> E pageMaps(E page, Wrapper<T> queryWrapper) {
        return null;
    }

    @Override
    default <E extends IPage<Map<String, Object>>> E pageMaps(E page) {
        return null;
    }

    @Override
    BaseMapper<T> getBaseMapper();

    @Override
    Class<T> getEntityClass();

    @Override
    default QueryChainWrapper<T> query() {
        return null;
    }

    @Override
    default LambdaQueryChainWrapper<T> lambdaQuery() {
        return null;
    }

    @Override
    default KtQueryChainWrapper<T> ktQuery() {
        return null;
    }

    @Override
    default KtUpdateChainWrapper<T> ktUpdate() {
        return null;
    }

    @Override
    default UpdateChainWrapper<T> update() {
        return null;
    }

    @Override
    default LambdaUpdateChainWrapper<T> lambdaUpdate() {
        return null;
    }

    @Override
    default boolean saveOrUpdate(T entity, Wrapper<T> updateWrapper) {
        return false;
    }
}
