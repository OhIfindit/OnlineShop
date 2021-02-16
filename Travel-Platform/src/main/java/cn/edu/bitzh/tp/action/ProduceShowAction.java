package cn.edu.bitzh.tp.action;

/**
 * @author 周锦涛
 * @date 2020年
 * 
 * 单个产品展示
*/




import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.bitzh.tp.model.CostContent;
import cn.edu.bitzh.tp.model.GraphicIntroduction;
import cn.edu.bitzh.tp.model.Produce;
import cn.edu.bitzh.tp.model.ProduceCost;
import cn.edu.bitzh.tp.service.ProduceRequestService;

public class ProduceShowAction extends ActionSupport{
	/* 产品基本信息实体类 */
	private Produce produce;
	/* 产品费用内容实体类 */
	private CostContent costContent;
	/* 产品图相关实体类 */
	private List<GraphicIntroduction> graphicIntroductions;
	private List<CostContent> costContents;
	private ProduceCost produceCost;
	private GraphicIntroduction graphicIntroduction;
	/* 页面传来产品编号 */
	private int produceIdIn;
	/* 页面传来产品编号转化string */
	private String produceId;
	/* 产品显示service */
	private ProduceRequestService produceRequestService;
	/* 宣传图列表 */
	private List<GraphicIntroduction> advertisingMap;
	
	public String execute(){
		
		/* 读取配置文件 */
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		/* 注入ProduceRequestService */
		produceRequestService = (ProduceRequestService) applicationContext.getBean("ProduceRequestService");
		
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		
		//实例化实体类对象
		graphicIntroductions = new ArrayList<GraphicIntroduction>();//
		produce=new Produce();
		produceCost =new ProduceCost();
		costContent=new CostContent();
		costContents=new ArrayList<CostContent>();
		advertisingMap = new ArrayList<GraphicIntroduction>();
		
		//经url传入产品id为
		produceId=""+produceIdIn;
		//执行产品信息查找
		boolean flag=produceRequestService.find(produceId, produce, produceCost, costContents, graphicIntroductions,advertisingMap);
		
		//查找失败
		if(!flag) {
		
		  System.out.println("查找失败\n");
		  return ERROR;
		}
		
		if(graphicIntroductions.size()>0) {
			System.out.println("ProduceShowAction +graphicIntroduction is"+graphicIntroductions.get(0).getGraphicIntroductionContent());
			
		}
		else {
			System.out.println("graphicIntroductions对象保存失败");
		}
		
		//将产品图文详情url保存到session
		for(int i=0;i<graphicIntroductions.size();i++) {
			
			session.setAttribute("graphicIntroductionContent"+i, graphicIntroductions.get(i).getGraphicIntroductionContent());
			
		}
		//将宣传图url保存到session
		for(int i=0;i<advertisingMap.size();i++) {
			
			session.setAttribute("advertisingMapContent"+i, advertisingMap.get(i).getGraphicIntroductionContent());
			
		}
			
		//将产品费用信息保存到session
		session.setAttribute("mealsCost",costContents.get(0).getMealsCost());
		session.setAttribute("accommodationFee", costContents.get(0).getAccommodationFee());
		session.setAttribute("landmarkTicket",costContents.get(0).getLandmarkTicket());
		session.setAttribute("carFare",costContents.get(0).getCarFare());
		session.setAttribute("guideFee",costContents.get(0).getGuideFee());
		
		
		//保存图文详情及宣传图数量到session
		session.setAttribute("demo_num", advertisingMap.size() );
		session.setAttribute("carousel_num", graphicIntroductions.size());
		
		
		//produce.getProduceMinPrice();
		
		
		
		//查找成功
		return SUCCESS;
		
		
		
		
	}
	
	
	
	public void test(Produce produce, ProduceCost produceCost, List<CostContent> costContent,List<GraphicIntroduction> graphicIntroductions) {
		
		
		System.out.println(produce+"\n");
		for(int i=0;i<costContent.size();i++) {
			System.out.println(costContent.get(i)+"\n");
			
		}
		for(int i=0;i<graphicIntroductions.size();i++) {
			System.out.println(graphicIntroductions.get(i)+"\n");
			
		}
		
		
	}

	public int getProduceIdIn() {
		return produceIdIn;
	}



	public void setProduceIdIn(int produceIdIn) {
		this.produceIdIn = produceIdIn;
	}



	public String getProduceId() {
		return produceId;
	}



	public void setProduceId(String produceId) {
		this.produceId = produceId;
	}



	public Produce getProduce() {
		return produce;
	}



	public void setProduce(Produce produce) {
		this.produce = produce;
	}



	public CostContent getCostContent() {
		return costContent;
	}



	public void setCostContent(CostContent costContent) {
		this.costContent = costContent;
	}



	public List<GraphicIntroduction> getGraphicIntroductions() {
		return graphicIntroductions;
	}



	public void setGraphicIntroductions(List<GraphicIntroduction> graphicIntroductions) {
		this.graphicIntroductions = graphicIntroductions;
	}



	public ProduceCost getProduceCost() {
		return produceCost;
	}



	public void setProduceCost(ProduceCost produceCost) {
		this.produceCost = produceCost;
	}



	public GraphicIntroduction getGraphicIntroduction() {
		return graphicIntroduction;
	}



	public void setGraphicIntroduction(GraphicIntroduction graphicIntroduction) {
		this.graphicIntroduction = graphicIntroduction;
	}



	public ProduceRequestService getProduceRequestService() {
		return produceRequestService;
	}



	public void setProduceRequestService(ProduceRequestService produceRequestService) {
		this.produceRequestService = produceRequestService;
	}
	

}
