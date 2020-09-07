package com.dataint.diseasepo.provider;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.diseasepo.model.form.EventForm;
import com.dataint.diseasepo.model.form.EventUpdateForm;
import com.dataint.diseasepo.model.param.EventQueryParam;
import org.springframework.stereotype.Component;


@Component
public interface EventProvider {


    ResultVO add( EventForm eventForm);


    ResultVO listAll(EventQueryParam eventQueryParam);


    ResultVO delete( Integer id);


    ResultVO updateEnabled(Integer id, String enabled);


    ResultVO updateEvent( EventUpdateForm eventUpdateForm);

}
