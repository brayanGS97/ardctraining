package com.ardctraining.storefront.controllers.pages;

import com.ardctraining.storefront.controllers.ControllerConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/final")

public class FeedbackPageController extends AbstractPageController {
    private static final String FEEDBACK_PAGE_LABEL="feedback";
    private static final String FEEDBACK_FORM_ATTR="feedbackForm";

    @GetMapping
    public String getFeedbackPageView( final Model model) throws CMSItemNotFoundException{
        final ContentPageModel contentPageModel =getContentPageForLabelOrId(FEEDBACK_PAGE_LABEL);
        storeCmsPageInModel(model, contentPageModel);
        setUpMetaDataForContentPage(model,contentPageModel);
       // model.addAttribute(FEEDBACK_FORM_ATTR, createEmptyForm());

        return ControllerConstants.Views.Pages.Feedback.FeedbackPage;
    }

    /*
    @PostMapping String submitFeedback(final Model model,
                                       final FeedbackForm feedbackForm,
                                       final BindingResult bindingResult,){
        if(BooleanUtils.isFalse(bindingResult.hasErrors())){

        }
        return "";
    }


    private FeedBackForm createEmptyForm() {
        final FeedbackForm feedbackForm=new FeedbackForm();
        feedbackForm.setSubject(StringUtils.EMPTY);
        feedbackForm.setMessage(StringUtils.EMPTY);
        return feedbackForm;
    }
     */
}
