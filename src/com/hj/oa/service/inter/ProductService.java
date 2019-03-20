/**   
* @Title: ProductService.java 
* @Package com.hj.oa.service.inter 
* @Description: TODO 
* @author mlsong   
* @date 2018年6月4日 上午8:36:33 
* @version V1.0   
*/
package com.hj.oa.service.inter;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hj.commons.OutstockStatus;
import com.hj.commons.ProductOutstockRecordStatus;
import com.hj.commons.ProductStatus;
import com.hj.oa.bean.ApprovalRecord;
import com.hj.oa.bean.Company;
import com.hj.oa.bean.Logistics;
import com.hj.oa.bean.OutstockDetail;
import com.hj.oa.bean.OutstockRecord;
import com.hj.oa.bean.Product;
import com.hj.oa.bean.ProductOutstockDetail;
import com.hj.oa.bean.ProductOutstockRecord;
import com.hj.oa.bean.Role;

/** 
* @ClassName: ProductService 
* @Description: 产品信息业务处理接口类
* @author mlsong 
* @date 2018年6月4日 上午8:36:33 
*/
public interface ProductService {

	/** 
	* @Description: 查询产品列表 
	* @param status
	* 		产品状态
	* @return Map<String, Object>
	* @author mlsong
	* @date 2018年6月4日 上午8:49:10
	*/
	Map<String, Object> getProducts(ProductStatus status);

	/** 
	* @Description: 保存申请单及发货详细产品信息
	* @param record
	* 			申请单
	* @param products
	* 			发货详细产品
	* @author mlsong
	* @date 2018年6月12日 上午10:51:28
	*/
	void saveOutstockRecordAndDetails(ProductOutstockRecord record, List<ProductOutstockDetail> products);

	/** 
	* @Description: 根据申请id查询申请记录
	* @param recordId
	* 			申请id
	* @return ProductOutstockRecord
	* @author mlsong
	* @date 2018年6月12日 上午11:09:01
	*/
	ProductOutstockRecord getProductOutstockRecordById(int recordId);

	/** 
	* @Description: 根据申请单查询申请单详细发货产品
	* @param recordId
	* @return List<ProductOutstockDetail>
	* @author mlsong
	* @date 2018年6月12日 上午11:16:00
	*/
	List<ProductOutstockDetail> getProductOutstockDetailsById(int recordId);

	/** 
	* @Description: 查询角色需要审批的所有发货申请记录 
	* @param roles
	* 			角色列表
	* @return List<ProductOutstockRecord>
	* @author mlsong
	* @date 2018年6月12日 上午11:52:57
	*/
	List<ProductOutstockRecord> getWillApprovalRecordsByRoles(List<Role> roles);

	/** 
	* @Description: 处理审批
	* @param approRecord
	* 			审批信息
	* @param record
	* 			发货申请记录
	* @param bt
	* 			发货时间
	* @param count
	* 			发货数量
	* @param jsonArr
	* 			详细数据
	* @param shipments
	* 			发货方式
	* @author mlsong
	* @date 2018年6月12日 下午12:50:54
	*/
	void handle(ApprovalRecord approRecord, ProductOutstockRecord record, String bt, Integer count, String jsonArr, Integer shipments);

	/** 
	* @Description: 根据订单状态查询发货申请记录 
	* @param status
	* @return List<ProductOutstockRecord>
	* @author mlsong
	* @date 2018年6月12日 下午2:58:18
	*/
	List<ProductOutstockRecord> getProductOutstockRecords(ProductOutstockRecordStatus...status);

	/** 
	* @Description: 根据申请单id查询所有发货批次记录
	* @param recordId
	* 			发货申请单ID
	* @return List<OutstockRecord>
	* @author mlsong
	* @date 2018年6月12日 下午3:07:40
	*/
	List<OutstockRecord> getOutstockRecordsByRecordId(int recordId);

	/** 
	* @Description: 处理确认物流回单
	* @param record
	* 			发货记录
	* @author mlsong
	* @date 2018年6月12日 下午3:37:29
	*/
	void handleReceive(OutstockRecord record);

	/** 
	* @Description: 查询用户申请的所有发货申请
	* @param userId
	* @return List<ProductOutstockRecord>
	* @author mlsong
	* @date 2018年6月12日 下午3:55:41
	*/
	List<ProductOutstockRecord> getProductOutstockRecordByUserId(int userId);

	/** 
	* @Description: 查询发货批次记录中的发货产品详细
	* @param recordId
	* 			发货批次记录id
	* @author mlsong
	* @date 2018年6月12日 下午4:19:16
	*/
	List<OutstockDetail> getOutstockDetailsByRecordId(int recordId);

	/** 
	* @Description: 确认发货处理
	* @param record
	* 			发货批次记录
	* @author mlsong
	* @date 2018年6月13日 上午8:43:58
	*/
	void handleOutstock(OutstockRecord record);
	
	/**
	* @Description: 构造订单编号 
	* @param orderHead
	* @return String
	* @author mlsong
	* @date 2018年6月20日 下午3:36:20
	 */
	String buildOrder(String orderHead);

	/** 
	* @Description: 修改产品状态
	* @param id
	* 		产品id
	* @return boolean
	* 		是否修改成功
	* @author mlsong
	* @date 2018年6月22日 下午2:43:45
	*/
	boolean changeProductState(Integer id);

	/** 
	* @Description: 根据id查询产品信息
	* @param id
	* 		产品id
	* @return Product
	* @author mlsong
	* @date 2018年6月22日 下午3:46:41
	*/
	Product getProductById(Integer id);

	/** 
	* @Description: 修改产品信息
	* @param product
	* @return boolean
	* @author mlsong
	* @date 2018年6月22日 下午4:24:12
	*/
	boolean updateProduct(Product product);

	/** 
	* @Description: 新增产品信息
	* @param product
	* @return boolean
	* @author mlsong
	* @date 2018年6月22日 下午5:29:41
	*/
	boolean addProduct(Product product);

	/** 
	* @Description: 查询所有收货单位信息
	* @param object
	* @return List<Company>
	* @author mlsong
	* @date 2018年6月25日 上午8:32:52
	*/
	List<Company> getCompanys(Integer state);

	/** 
	* @Description: 查询收货单位信息
	* @param id
	* @return Company
	* @author mlsong
	* @date 2018年6月25日 上午9:30:30
	*/
	Company getCompanyById(Integer id);

	/** 
	* @Description: 添加收货单位
	* @param company
	* @return boolean
	* @author mlsong
	* @date 2018年6月25日 上午9:38:45
	*/
	boolean addCompany(Company company);

	/** 
	* @Description: 修改收货单位信息
	* @param company
	* @return boolean
	* @author mlsong
	* @date 2018年6月25日 上午10:06:56
	*/
	boolean updateCompany(Company company);

	/** 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param approRecord
	* @param record
	* @param productIds
	* @param counts
	* @param units
	* @param beginDate void
	* @author mlsong
	* @date 2018年6月26日 下午3:22:19
	*/
	void handle(ApprovalRecord approRecord, ProductOutstockRecord record, Integer[] productIds, Integer[] counts,
			String[] units, String beginDate,String[] wxbh,List<String> urls,String[] fhDates);
	
	/** 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param status
	* @return List<OutstockRecord>
	* @author mlsong
	* @date 2018年6月27日 上午11:02:54
	*/
	List<OutstockRecord> getOutstockRecordsByStatus(OutstockStatus...status);

	/** 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param recordId
	* @return OutstockRecord
	* @author mlsong
	* @date 2018年6月27日 下午2:51:28
	*/
	OutstockRecord getOutstockRecordById(int recordId);

	/**
	 * 
	* @Description: 按条件查询产品信息 
	* @param product
	* @return Map<String,Object>
	* @author mlsong
	* @date 2018年7月4日 下午4:39:41
	 */
	Map<String, Object> getProducts(Product product);

	/**
	* @Description: 查询产品总条数
	* @param product
	* @return int
	* @author mlsong
	* @date 2018年7月5日 下午2:53:29
	 */
	int getProductCount(Product product);
	
	void updateProductRecordByid(Integer id,String time);
	
	String selectShTime(Integer id);
	
	List<Logistics> findAllLogistics();
}
