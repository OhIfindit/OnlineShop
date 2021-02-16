package cn.edu.bitzh.tp.action;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import cn.edu.bitzh.tp.model.Region;
import cn.edu.bitzh.tp.service.IRegionService;
import cn.edu.bitzh.tp.service.impl.RegionService;

/**
 * @author 古学懂_Victor
 * @date 2020年
 */
public class RegionAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	private IRegionService rs = (RegionService) applicationContext.getBean("regionService");
	private Region region;
	private List<Region> regions;
	/**
	 * type可以取值"listContinents", "listChildren"或不设置
	 */
	private String type = "";
	/**
	 * 若type为"listChildren", 需要提供pid
	 */
	private int pid = 0;
	/**
	 * 若type为空, 需要提供id
	 */
	private int id = 0;

	public RegionAction() {
		System.out.println("RegionAction");
	}

	/**
	 * 
	 */
	public String execute() {
		if (type.equals("listContinents")) {
			regions = rs.listContinents();
		} else if (type.equals("listChildren")) {
			regions = rs.listChildRegions(pid);
		} else {
			region = rs.get(id);
		}
		System.out.println("Execute " + this.type);
		return Action.SUCCESS;
	}

	/**
	 * 获取大洲列表
	 * 
	 * @author 古学懂_Victor
	 * @date 2020年
	 * @return
	 */
	public String listContinents() {
		System.out.println("listContinents");
		this.regions = rs.listContinents();
		return ActionSupport.SUCCESS;
	}

	/**
	 * 获取子地区列表，需要提供父级地区id
	 * 
	 * @author 古学懂_Victor
	 * @date 2020年
	 * @return
	 * @see IRegionService
	 */
	public String listChildren() {
		System.out.println("listChildren");
		this.regions = rs.listChildRegions(this.pid);
		return ActionSupport.SUCCESS;
	}

	/**
	 * 根据id获取地区
	 * 
	 * @author 古学懂_Victor
	 * @date 2020年
	 * @return
	 */
	public String get() {
		System.out.println("getRegion");
		this.region = rs.get(this.id);
		return ActionSupport.SUCCESS;
	}

	@JSON(serialize = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JSON(serialize = false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public List<Region> getRegions() {
		return regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

	@JSON(serialize = false)
	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

}
