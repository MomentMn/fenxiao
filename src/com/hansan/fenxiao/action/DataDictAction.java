package com.hansan.fenxiao.action;

import com.alibaba.fastjson.JSONArray;
import com.hansan.fenxiao.dto.Areas;
import com.hansan.fenxiao.dto.Cities;
import com.hansan.fenxiao.dto.CityDto;
import com.hansan.fenxiao.entities.DataDict;
import com.hansan.fenxiao.mybatisservice.IDataDictService;
import com.hansan.fenxiao.utils.BjuiJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @description:
 * @projectName: com.hansan.fenxiao.action
 * @className: fenxiao
 * @author:谭农春
 * @createTime:2018/6/2 17:06
 */
@Controller("dataDictAction")
@Scope("prototype")
public class DataDictAction extends BaseAction {

  @Autowired
  private IDataDictService dataDictService;
  /**
   *  获取快递列表
   *   http://localhost:8080/datadict/listExpress
   */
  public void listExpress() {
    List<DataDict> list = dataDictService.listExpress();
    // 序列化为JSON
    renderJson(list);
  }

  /**
   *  上级地区
   */
 private String parentCode;
  /**
   *  获取地区列表
   *   http://localhost:8080/datadict/listExpress
   */
  public void listArea() {

    List<DataDict> list = dataDictService.listArea(parentCode);
    // 序列化为JSON
    renderJson(list);
  }

    public void list() {
        List<CityDto> dtoList = new ArrayList<>();
        //查询所有的省
        List<DataDict> dataDictList1 = dataDictService.listArea(parentCode);

        for (DataDict dataDict1 : dataDictList1) {
            CityDto cityDto = new CityDto();
            cityDto.setProvinceName(dataDict1.getName());
            cityDto.setProvinceId(dataDict1.getCode());

            List<Cities> citiesList = new ArrayList<>();
            //查询省下的市
            List<DataDict> dataDictList2 = dataDictService.listArea(dataDict1.getCode());

            if (!CollectionUtils.isEmpty(dataDictList2)) {
                List<Areas> areasList1 = new ArrayList<>();
                Cities cities1 = new Cities();
                cities1.setCityName(dataDict1.getName());
                cities1.setCityId(dataDict1.getCode());
                for (DataDict dataDict2 : dataDictList2) {
                    //查询市下面的县
                    List<DataDict> dataDictList3 = dataDictService.listArea(dataDict2.getCode());
                    if (!CollectionUtils.isEmpty(dataDictList3)) {
                        Cities cities = new Cities();
                        cities.setCityName(dataDict2.getName());
                        cities.setCityId(dataDict2.getCode());
                        List<Areas> areasList = new ArrayList<>();
                        for (DataDict dataDict3 : dataDictList3) {
                            Areas areas = new Areas();
                            areas.setAreaName(dataDict3.getName());
                            areas.setAreaId(dataDict3.getCode());
                            areasList.add(areas);
                        }
                        cities.setAreas(areasList);
                        citiesList.add(cities);
                    } else {
                        Areas areas = new Areas();
                        areas.setAreaName(dataDict2.getName());
                        areas.setAreaId(dataDict2.getCode());
                        areasList1.add(areas);
                    }
                }
                cities1.setAreas(areasList1);
                if(citiesList.size()==0){
                    citiesList.add(cities1);
                }
            }
            cityDto.setCities(citiesList);
            dtoList.add(cityDto);
        }
        renderJson(dtoList);
        System.out.println("省市加载完成");

    }

  public String getParentCode() {
    return parentCode;
  }

  public void setParentCode(String parentCode) {
    this.parentCode = parentCode;
  }
}
