package com.lsf.imf.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 导出Excel文档工具类
 * @author mengjie
 * */
public class ExcelUtil {
 
    /**
     * 创建excel文档，
     * @param list 数据
     * @param keys list中map的key数组集合
     * @param columnNames excel的列名
     * */
private final static Logger logger=LoggerFactory.getLogger(ExcelUtil.class);
	
	public static enum ExcelStyle {TITLE_STYLE,VALUE_STYLE}
	
	public static LinkedHashMap<String,String> afdsTitleMap=new LinkedHashMap<String,String>();
	
	static{
		afdsTitleMap.put("flight_identity","航班号");
		afdsTitleMap.put("flight_direction","进出港方向");
		afdsTitleMap.put("flight_repeat_count","航班重复次数");
		afdsTitleMap.put("scheduled_date","计划日期");
		afdsTitleMap.put("iata_carrier_iata_code","承运人iata代码");
		afdsTitleMap.put("iata_flight_number","航班数字编号");
		afdsTitleMap.put("iata_flight_suffix","iata后缀");
		afdsTitleMap.put("icao_carrier_icao_code","承运人icao代码");
		afdsTitleMap.put("icao_flight_number","航班数字编号");
		afdsTitleMap.put("icao_flight_suffix","icao后缀");
		afdsTitleMap.put("aircraft_callsign","飞机呼叫号");
		afdsTitleMap.put("aircraft_owner_iata_code","飞机所属人iata代码");
		afdsTitleMap.put("aircraft_passenger_capacity","飞机最大旅客容量");
		afdsTitleMap.put("aircraft_registration","飞机注册号");
		afdsTitleMap.put("aircraft_subtype_iata_code","子机型iata代码");
		afdsTitleMap.put("aircraft_type_icao_code","机型icao代码");
		afdsTitleMap.put("aircraft_operator","飞机运营人");
		afdsTitleMap.put("aircraft_terminal_code","机型航站楼代码");
		afdsTitleMap.put("airline_terminal_code","航空公司航站楼代码");
		afdsTitleMap.put("airport_iata_code","机场iata代码");
		afdsTitleMap.put("passenger_terminal_code","旅客航站楼代码");
		afdsTitleMap.put("runway_id","跑道id");
		afdsTitleMap.put("secure_stand_is_required","是否需要安全机位");
		afdsTitleMap.put("stand_position","机位id");
		afdsTitleMap.put("supplementary_infomation_text","补充信息文本");
		afdsTitleMap.put("supplementary_infomation_type","补充信息类型");
		afdsTitleMap.put("baggage_makeup_close_date_time","行李分拣带的计划关闭时间");
		afdsTitleMap.put("baggage_makeup_open_date_time","行李分拣带的计划开放时间");
		afdsTitleMap.put("baggage_makeup_position_id","行李分拣带代码");
		afdsTitleMap.put("baggage_makeup_position_role","行李分拣带位置角色");
		afdsTitleMap.put("baggage_reclaim_carousel_id","行李提取转盘代码");
		afdsTitleMap.put("baggage_reclaim_carousel_role","行李提取转盘职能");
		afdsTitleMap.put("baggage_reclaim_close_dt","行李提取转盘关闭时间");
		afdsTitleMap.put("baggage_reckaim_open_dt","行李提取转盘开放时间");
		afdsTitleMap.put("exit_door_number","出口编号");
		afdsTitleMap.put("first_bag_date_time","第一件行李的时间");
		afdsTitleMap.put("last_bag_date_time","最后一件行李的时间");
		afdsTitleMap.put("baggage_terminal_code","行李航站楼代码");
		afdsTitleMap.put("bus_is_required","是否需要摆渡车");
		afdsTitleMap.put("checkin_close_date_time","值机柜台关闭时间");
		afdsTitleMap.put("checkin_cluster_id","值机组id");
		afdsTitleMap.put("checkin_desk_range","值机柜台范围");
		afdsTitleMap.put("checkin_open_date_time","值机柜台开放时间");
		afdsTitleMap.put("checkin_role","值机柜台角色");
		afdsTitleMap.put("checkin_type_code","值机柜台类型代码");
		afdsTitleMap.put("gate_boarding_status","登机状态");
		afdsTitleMap.put("gate_close_date_time","登机口关闭时间");
		afdsTitleMap.put("gate_number","登机口编号");
		afdsTitleMap.put("gate_open_date_time","登机口开放时间");
		afdsTitleMap.put("gate_role","登机口角色");
		afdsTitleMap.put("remark_code","备注代码");
		afdsTitleMap.put("remark_type","备注类型");
		afdsTitleMap.put("account_code","账号代码");
		afdsTitleMap.put("code_share_status","代码共享状态");
		afdsTitleMap.put("divert_airport_iata_code","改降机场iata代码");
		afdsTitleMap.put("flight_cancel_code","不在营运的航班");
		afdsTitleMap.put("flight_classification_code","航班类别代码");
		afdsTitleMap.put("flight_nature_code","航班的性质代码");
		afdsTitleMap.put("flight_operates_overnight","航班过夜");
		afdsTitleMap.put("flight_sector_code","航班营运的区间");
		afdsTitleMap.put("flight_service_type_iata_code","航班服务的iata代码");
		afdsTitleMap.put("flight_status_code","航班状态");
		afdsTitleMap.put("flight_transit_code","航班过站代码");
		afdsTitleMap.put("new_flight_reason","新增航班原因");
		afdsTitleMap.put("irregularity_alpha_iata_code","不规则的alphaiata代码");
		afdsTitleMap.put("irregularity_duration","不规则的区间");
		afdsTitleMap.put("irregularity_numeric_iata_code","不规则的数字iata代码");
		afdsTitleMap.put("irregularity_role","不规则的角色");
		afdsTitleMap.put("is_general_aviation_flight","是否通用飞行航班");
		afdsTitleMap.put("is_return_flight","是否返航航班");
		afdsTitleMap.put("is_transfer_flight","是否技术型过站");
		afdsTitleMap.put("linked_carrier_iata_code","连班承运人的iata代码");
		afdsTitleMap.put("linked_flight_number","连班航班数字编号");
		afdsTitleMap.put("linked_flight_suffix","连班后缀");
		afdsTitleMap.put("linked_flight_identity","连班航班号");
		afdsTitleMap.put("linked_flight_repeat_count","连班航班重复次数");
		afdsTitleMap.put("linked_scheduled_date","连班计划日期");
		afdsTitleMap.put("master_carrier_iata_code","主航班承运人的iata代码");
		afdsTitleMap.put("master_flight_number","主航班航班数字编号");
		afdsTitleMap.put("master_flight_suffix","主航班后缀");
		afdsTitleMap.put("master_flight_identity","主航班航班号");
		afdsTitleMap.put("master_flight_repeat_count","主航班航班重复次数");
		afdsTitleMap.put("handling_agent_iata_code","地面代理服务代码");
		afdsTitleMap.put("handing_agent_service","地面代理服务种类");
		afdsTitleMap.put("operation_type_code","营运状态");
		afdsTitleMap.put("operation_type_role","运营类型角色");
		afdsTitleMap.put("sf_carrier_iata_code","主航班承运人的iata代码");
		afdsTitleMap.put("sf_flight_number","共享航班航班数字编号");
		afdsTitleMap.put("sf_flight_suffix","共享航班后缀");
		afdsTitleMap.put("sf_flight_identity","共享航班航班号");
		afdsTitleMap.put("sf_flight_repeat_count","共享航班航班重复次数");
		afdsTitleMap.put("transfer_flight_bag_count","中转航班行李数量");
		afdsTitleMap.put("transfer_flight_identity","中转航班标识符");
		afdsTitleMap.put("transfer_flight_passenger_ct","中转航班旅客数量");
		afdsTitleMap.put("total_passenger_count","全员总计");
		afdsTitleMap.put("total_crew_count","机组人员数量");
		afdsTitleMap.put("total_baggage_count","行李总件数");
		afdsTitleMap.put("total_baggage_weight","行李总重量");
		afdsTitleMap.put("total_freight_weight","货物总重量");
		afdsTitleMap.put("total_load_weight","装载总重量");
		afdsTitleMap.put("total_mail_weight","邮件总重量");
		afdsTitleMap.put("transfer_baggage_count","中转行李数量");
		afdsTitleMap.put("transfer_baggage_weight","中转行李重量");
		afdsTitleMap.put("transfer_cargo_weight","中转货物重量");
		afdsTitleMap.put("transfer_mail_weight","中转邮件重量");
		afdsTitleMap.put("transit_baggage_count","过站行李数量");
		afdsTitleMap.put("transit_baggage_weight","过站行李重量");
		afdsTitleMap.put("adult_passenger_count","成人旅客数");
		afdsTitleMap.put("business_class_passenger_count","公务舱旅客总数");
		afdsTitleMap.put("child_passenger_count","儿童旅客数");
		afdsTitleMap.put("domestic_passenger_count","国内旅客数");
		afdsTitleMap.put("economy_class_passenger_count","经济舱旅客数");
		afdsTitleMap.put("first_class_passenger_count","头等舱旅客数");
		afdsTitleMap.put("infant_passenger_count","婴儿旅客数");
		afdsTitleMap.put("international_passenger_count","国际旅客数");
		afdsTitleMap.put("local_passenger_count","当地旅客数");
		afdsTitleMap.put("non_transfer_passenger_count","非中转旅客数");
		afdsTitleMap.put("transfer_passenger_count","中转旅客数");
		afdsTitleMap.put("transit_passenger_count","过站旅客数");
		afdsTitleMap.put("wheel_chair_passenger_count","轮椅旅客数");
		afdsTitleMap.put("actual_off_blocks_date_time","实际撤轮挡时间");
		afdsTitleMap.put("actual_on_blocks_date_time","实际上轮挡时间");
		afdsTitleMap.put("estimated_date_time","预计时间");
		afdsTitleMap.put("estimated_flight_duration","预计飞行时间");
		afdsTitleMap.put("final_approach_date_time","最后靠近时间");
		afdsTitleMap.put("latest_known_date_time","最新进港或出港时间");
		afdsTitleMap.put("latest_known_date_time_source","最新进港或出港时间的信息源");
		afdsTitleMap.put("next_information_dt","下一条信息时间");
		afdsTitleMap.put("next_station_actual_dt","后站实际时间");
		afdsTitleMap.put("next_station_estimated_dt","后站预计时间");
		afdsTitleMap.put("next_station_scheduled_dt","后站计划时间");
		afdsTitleMap.put("previous_station_airborne_dt","前站起飞时间");
		afdsTitleMap.put("previous_station_estimated_dt","前站预计时间");
		afdsTitleMap.put("previous_station_scheduled_dt","前站计划时间");
		afdsTitleMap.put("scheduled_date_time","计划时间");
		afdsTitleMap.put("ten_mile_out_date_time","十海里时间");
		afdsTitleMap.put("wheels_down_date_time","落地时间");
		afdsTitleMap.put("wheels_up_date_time","起飞时间");
		afdsTitleMap.put("port_of_call_iata_code","经停机场iata代码");
		afdsTitleMap.put("port_of_call_icao_code","经停机场icao代码");
		afdsTitleMap.put("public_flight_identity","可公布的航班id");
		afdsTitleMap.put("public_carrier_code","可公布的承运人代码");
		afdsTitleMap.put("public_date_time","可公布的时间");
	}
	
	public static Workbook createWorkBook(List<HashMap<String,String>> list){
		Workbook wb = new HSSFWorkbook();
		
//		HashMap<ExcelStyle,CellStyle> styles=initStyle(wb);

		// 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet("sheet1");
        
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<afdsTitleMap.keySet().size();i++){
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }
 
        // 创建第一行
        Row titleCNRow = sheet.createRow(0);
        Row titleENRow = sheet.createRow(1);

        //设置列名
        String[] keys=afdsTitleMap.keySet().toArray(new String[0]);
        for(int i=0;i<keys.length;i++){      
            Cell cellCn = titleCNRow.createCell(i);
            cellCn.setCellValue(afdsTitleMap.get(keys[i]));
            
            Cell cellEn = titleENRow.createCell(i);            
            cellEn.setCellValue(keys[i]);
//            cell.setCellStyle(styles.get(ExcelStyle.TITLE_STYLE));
        	
        }
        //设置每行每列的值
        for (int i = 0; i < list.size(); i++) {
        	HashMap<String,String> rec=list.get(i);
            Row dataRow = sheet.createRow(2+i);

            for(int j=0;j<keys.length;j++){
            	String val=rec.get(keys[j]);
                Cell cell = dataRow.createCell(j);
                cell.setCellValue(val== null?"": val);
                
//                cell.setCellStyle(styles.get(ExcelStyle.VALUE_STYLE));
            }
        }
        
        return wb;
	}
	
	private static HashMap<ExcelStyle,CellStyle> initStyle(Workbook wb){
		HashMap<ExcelStyle,CellStyle> styles=new HashMap<ExcelStyle,CellStyle>();
		// 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
 
        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
 
        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);
 
        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);
 
        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        styles.put(ExcelStyle.TITLE_STYLE, cs);
        styles.put(ExcelStyle.VALUE_STYLE, cs2);
        
        return styles;
        
	}
	public static InputStream getExcelInputStream(Workbook wb){
		
		try {			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
		
			wb.write(os);
	
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			
			return is;
		} catch (final IOException e) {
			logger.error("Exception Throwable", e);
			return null;
		}
	}

}