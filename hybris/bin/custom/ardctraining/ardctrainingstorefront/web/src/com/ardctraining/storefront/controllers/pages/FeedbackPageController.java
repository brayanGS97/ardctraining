package com.ardctraining.storefront.controllers.pages;

import com.ardctraining.core.feedback.service.CustomerFeedbackService;
import com.ardctraining.core.model.CustomerFeedbackModel;
import com.ardctraining.facades.feedback.ArdctrainningFeedbackFacade;
import com.ardctraining.facades.feedback.CustomerFeedbackData;
import com.ardctraining.storefront.controllers.ControllerConstants;
import com.ardctraining.storefront.forms.FeedbackForm;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;


@Controller
@RequestMapping(value = "/feedback")
public class FeedbackPageController extends AbstractPageController {
    private static final String FEEDBACK_PAGE_LABEL="feedback";
    private static final String FEEDBACK_FORM_ATTR="feedbackForm";
    private static final String REDIRECT="redirect:/feedback";

    @Resource(name="feedbackFacade")
    private ArdctrainningFeedbackFacade feedbackFacade;

    @Resource(name = "customerFeedbackService")
    private CustomerFeedbackService customerFeedbackService;



    @GetMapping
    public String getFeedbackPageView( final Model model) throws CMSItemNotFoundException{
        final ContentPageModel contentPageModel =getContentPageForLabelOrId(FEEDBACK_PAGE_LABEL);
        storeCmsPageInModel(model, contentPageModel);
        setUpMetaDataForContentPage(model,contentPageModel);
        model.addAttribute(FEEDBACK_FORM_ATTR, createEmptyForm());
        model.addAttribute("feedbacks",feedbackFacade.getCustomerFeedback());

        return ControllerConstants.Views.Pages.Feedback.FeedbackPage;
    }


    @PostMapping("/save")
    public String submitFeedback(HttpSession session,@ModelAttribute FeedbackForm form) throws CMSItemNotFoundException {
        final ContentPageModel contentPageModel =getContentPageForLabelOrId(FEEDBACK_PAGE_LABEL);
        if(StringUtils.isNotEmpty(form.getSubject()) && StringUtils.isNotEmpty(form.getMessage())) {
            CustomerFeedbackData customerFeedbackData = new CustomerFeedbackData();
            customerFeedbackData.setSubject(form.getSubject());
            customerFeedbackData.setMessage(form.getMessage());
            feedbackFacade.save(customerFeedbackData);
            session.setAttribute("newFeedback",true);
        }else{
            session.setAttribute("badFeedback",true);
        }
        return REDIRECT;
    }


    private FeedbackForm createEmptyForm() {
        final FeedbackForm feedbackForm=new FeedbackForm();
        feedbackForm.setSubject(StringUtils.EMPTY);
        feedbackForm.setMessage(StringUtils.EMPTY);
        return feedbackForm;
    }

}
