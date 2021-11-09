jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SubscriptionTmpBillingService } from '../service/subscription-tmp-billing.service';
import { ISubscriptionTmpBilling, SubscriptionTmpBilling } from '../subscription-tmp-billing.model';

import { SubscriptionTmpBillingUpdateComponent } from './subscription-tmp-billing-update.component';

describe('SubscriptionTmpBilling Management Update Component', () => {
  let comp: SubscriptionTmpBillingUpdateComponent;
  let fixture: ComponentFixture<SubscriptionTmpBillingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let subscriptionTmpService: SubscriptionTmpBillingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SubscriptionTmpBillingUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(SubscriptionTmpBillingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SubscriptionTmpBillingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    subscriptionTmpService = TestBed.inject(SubscriptionTmpBillingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const subscriptionTmp: ISubscriptionTmpBilling = { id: 'CBA' };

      activatedRoute.data = of({ subscriptionTmp });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(subscriptionTmp));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubscriptionTmpBilling>>();
      const subscriptionTmp = { id: 'ABC' };
      jest.spyOn(subscriptionTmpService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subscriptionTmp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subscriptionTmp }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(subscriptionTmpService.update).toHaveBeenCalledWith(subscriptionTmp);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubscriptionTmpBilling>>();
      const subscriptionTmp = new SubscriptionTmpBilling();
      jest.spyOn(subscriptionTmpService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subscriptionTmp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subscriptionTmp }));
      saveSubject.complete();

      // THEN
      expect(subscriptionTmpService.create).toHaveBeenCalledWith(subscriptionTmp);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubscriptionTmpBilling>>();
      const subscriptionTmp = { id: 'ABC' };
      jest.spyOn(subscriptionTmpService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subscriptionTmp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(subscriptionTmpService.update).toHaveBeenCalledWith(subscriptionTmp);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
