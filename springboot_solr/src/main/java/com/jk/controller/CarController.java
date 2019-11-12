package com.jk.controller;

import com.jk.model.Car;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("solr")
public class CarController {
    @Autowired
    private SolrClient solrClient;

    @RequestMapping("toIndex")
    public String toIndex(){
        return "index";
    }

    @RequestMapping("searchCar")
    @ResponseBody
    public Map<String,Object> searchCar(Car car,Integer page,Integer rows){

        Map<String,Object> map1 = new HashMap<String,Object>();
        try {

//            存放所有查询条件
            SolrQuery solrQuery =  new SolrQuery();
            if(car.getCarname() != null && !"".equals(car.getCarname())){
                //查询条件 这里的Q对应标红的的地方
                solrQuery.set("q",car.getCarname());
            }else{
                solrQuery.set("q","*:*");
            }
//排序
            //solrQuery.addSort("")
            //分页
            if(page == null){
                solrQuery.setStart(0);
            }else {
                solrQuery.setStart((page-1)*rows);
            }
            if(rows == null){
                solrQuery.setRows(5);
            }else {
                solrQuery.setRows(rows);
            }

            //默认域
            solrQuery.set("df","carname");

            //打开高亮开关
            solrQuery.setHighlight(true);

            //指定高亮区域
            solrQuery.addHighlightField("carname");

            //设置前缀
            solrQuery.setHighlightSimplePre("<span style='color:red'>");

            //设置后缀
            solrQuery.setHighlightSimplePost("</span>");

            //查询并返会
            QueryResponse queryResponse= solrClient.query("core1", solrQuery);

            //查询后返回的对象，获得真正的返回结果
            SolrDocumentList results = queryResponse.getResults();

            //查询总条数
            long numFound = results.getNumFound();

            //获取高亮显示的结果, 高亮显示的结果和查询结果是分开放的

            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

            //创建list集合接收我们循环的参数
            List<Car> list1 = new ArrayList<Car>();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            for (SolrDocument result:results){

                Map<String, List<String>> map = highlighting.get(result.get("id"));

                List<String> stringList = map.get("carname");

                Car car1 = new Car();
                String highFile = "";
                if(stringList == null){
                    highFile = result.get("carname").toString();
                }else {
                    highFile = stringList.get(0);
                }

                car1.setCarid(Integer.parseInt(result.get("id").toString()));
                car1.setCarname(highFile);
                car1.setCarsales(Integer.parseInt(result.get("carsales").toString()));
                car1.setCartype(result.get("cartype").toString());
                car1.setCartime(result.get("cartime").toString());

                list1.add(car1);
            }
            map1.put("total",numFound);
            map1.put("rows",list1);
            return map1;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
