package fortect.mscm.base.test;/**
 * @Description:
 * @Author: chen.chen
 * @CreateDate: 2019/10/31 16:30
 * @Version: 1.0
 */

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import fortec.common.core.excel.handler.ExcelDictHandlerImpl;
import fortec.common.starter.MainApplication;
import fortec.mscm.base.request.MaterialQueryRequest;
import fortec.mscm.base.request.MaterialSpecQueryRequest;
import fortec.mscm.base.service.MaterialService;
import fortec.mscm.base.service.MaterialSpecService;
import fortec.mscm.base.vo.MaterialSpecVO;
import fortec.mscm.base.vo.MaterialVO;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 *@ClassName EasyPoiTest
 *@Description TODO
 *@Author Chenchen
 *@Date 2019/10/31 16:30
 */
@ComponentScan({"fortec.mscm.base.service"})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class EasyPoiTest {

    @Autowired
    private MaterialService materialService;
    @Autowired
    private MaterialSpecService materialSpecService;

    @Test
    public void test() throws IOException {
        MaterialQueryRequest request = new MaterialQueryRequest();
        List<MaterialVO> list = materialService.list(request);

        for (MaterialVO materialVO : list) {
            List<MaterialSpecVO> specs = materialSpecService.list(new MaterialSpecQueryRequest().setMaterialId(materialVO.getId()));
            materialVO.setSpecs(specs);
        }
        ExportParams exportParams = new ExportParams("商品", "商品");
        exportParams.setDictHandler(ExcelDictHandlerImpl.getInstance());
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,
                MaterialVO.class, list);
        File savefile = new File("C:/Users/DELL/Desktop");
        if (!savefile.exists()) {
            boolean result = savefile.mkdirs();
            System.out.println("目录不存在，创建" + result);
        }
        FileOutputStream fos = new FileOutputStream("C:/Users/DELL/Desktop/material.xls");
        workbook.write(fos);
        fos.close();
    }
}
