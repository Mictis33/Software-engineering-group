package com.itheima.service.impl;

import com.itheima.mapper.BrandMapper;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.service.BrandService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


import java.util.List;

public class BrandServiceImpl implements BrandService {

    /*创建SqlSessionFactory工厂对象*/
    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();

    @Override
    public List<Brand> selectAll() {
        /*获取sqlSession对象*/
        SqlSession sqlSession=factory.openSession();
        /*获取BrandMapper*/
        BrandMapper mapper=sqlSession.getMapper(BrandMapper.class);
        /*调用资源*/
        List<Brand> brands=mapper.selectAll();
        /*释放资源*/
        sqlSession.close();

        return brands;
    }

    @Override
    public void update(Brand brand) {
        /*获取sqlSession对象*/
        SqlSession sqlSession=factory.openSession();
        /*获取BrandMapper*/
        BrandMapper mapper=sqlSession.getMapper(BrandMapper.class);
        /*调用方法*/
        mapper.update(brand);
        /*提交事务*/
        sqlSession.commit();
        /*释放资源*/
        sqlSession.close();
    }

    @Override
    public void deleteById(int id) {
        /*获取sqlSession对象*/
        SqlSession sqlSession=factory.openSession();
        /*获取BrandMapper*/
        BrandMapper mapper=sqlSession.getMapper(BrandMapper.class);
        /*调用方法*/
        mapper.deleteById(id);
        /*提交事务*/
        sqlSession.commit();
        /*释放资源*/
        sqlSession.close();
    }


    @Override
    public void add(Brand brand) {
        /*获取sqlSession对象*/
        SqlSession sqlSession=factory.openSession();
        /*获取BrandMapper*/
        BrandMapper mapper=sqlSession.getMapper(BrandMapper.class);
        /*调用方法*/
        mapper.add(brand);
        /*提交事务*/
        sqlSession.commit();
        /*释放资源*/
        sqlSession.close();
    }

    @Override
    public void deleteByIds(int[] ids) {
        /*获取sqlSession对象*/
        SqlSession sqlSession=factory.openSession();
        /*获取BrandMapper*/
        BrandMapper mapper=sqlSession.getMapper(BrandMapper.class);
        /*调用方法*/
        mapper.deleteByIds(ids);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public PageBean<Brand> selectByPage(int currentPage, int pageSize) {
        //2. 获取SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //3. 获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);


        //4. 计算开始索引
        int begin = (currentPage - 1) * pageSize;
        // 计算查询条目数
        int size = pageSize;

        //5. 查询当前页数据
        List<Brand> rows = mapper.selectByPage(begin, size);

        //6. 查询总记录数
        int totalCount = mapper.selectTotalCount();

        //7. 封装PageBean对象
        PageBean<Brand> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);


        //8. 释放资源
        sqlSession.close();

        return pageBean;
    }

    /*分页按条件查询*/
    /*参数都自浏览器传来，在本方法中实现的功能是：根据currentPage和pageSize算出在数据库中查询的
    * 开始索引和查询条目数量。把用户输入的brand中的信息拼接字符串变成模糊查询
    * 然后根据上面的信息调用Mapper查询，查出来的当前页数据是一个List,放在PageBean的rows成员，同时查找符合现在条件的总记录
    * 数，放在PageBean的totalCount成员，返回一个PageBean对象*/
    @Override
    public PageBean<Brand> selectByPageAndCondition(int currentPage, int pageSize, Brand brand) {
        //2. 获取SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //3. 获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);


        //4. 计算开始索引（从数据库中的第几条开始）
        int begin = (currentPage - 1) * pageSize;
        // 计算查询条目数
        int size = pageSize;

        // 处理brand条件，模糊表达式（用户输入能实现模糊查询）
        String brandName = brand.getBrandName();
        if (brandName != null && brandName.length() > 0) {
            brand.setBrandName("%" + brandName + "%");
        }

        String companyName = brand.getCompanyName();
        if (companyName != null && companyName.length() > 0) {
            brand.setCompanyName("%" + companyName + "%");
        }


        //5. 查询当前页数据（查出来的是一个list）
        List<Brand> rows = mapper.selectByPageAndCondition(begin, size, brand);

        //6. 查询总记录数（根据用户输入的查询）
        int totalCount = mapper.selectTotalCountByCondition(brand);

        //7. 封装PageBean对象
        PageBean<Brand> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);


        //8. 释放资源
        sqlSession.close();

        return pageBean;
    }


}
