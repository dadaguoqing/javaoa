<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

				<input name="endDate" id="et" value="${endDate }" style="width:115px" class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
				<select name="endHour" id="endHour" >
					<option value="17时">17时</option>
					<option value="08时">8时</option> <option value="09时">9时</option> <option value="10时">10时</option>
					<option value="11时">11时</option> <option value="12时">12时</option> <option value="13时">13时</option>
					<option value="14时">14时</option> <option value="15时">15时</option> <option value="16时">16时</option>
				</select>
				<select name="endMin" id="endMin">
					<option value="30分">30分</option> <option value="00分">0分</option> 
				</select>
				<script>
				var endHour = '${endHour}' == '' ? '17时' :  '${endHour}';
				var endMin = '${endMin}' == '' ? '30分' : '${endMin}';
				$('#endHour').val(endHour);
				$('#endMin').val(endMin);
				</script>