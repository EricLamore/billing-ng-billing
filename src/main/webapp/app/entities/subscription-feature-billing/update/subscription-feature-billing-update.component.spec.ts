jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SubscriptionFeatureBillingService } from '../service/subscription-feature-billing.service';
import { ISubscriptionFeatureBilling, SubscriptionFeatureBilling } from '../subscription-feature-billing.model';

import { SubscriptionFeatureBillingUpdateComponent } from './subscription-feature-billing-update.component';

describe('SubscriptionFeatureBilling Management Update Component', () => {
  let comp: SubscriptionFeatureBillingUpdateComponent;
  let fixture: ComponentFixture<SubscriptionFeatureBillingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let subscriptionFeatureService: SubscriptionFeatureBillingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SubscriptionFeatureBillingUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(SubscriptionFeatureBillingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SubscriptionFeatureBillingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    subscriptionFeatureService = TestBed.inject(SubscriptionFeatureBillingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const subscriptionFeature: ISubscriptionFeatureBilling = { id: 'CBA' };

      activatedRoute.data = of({ subscriptionFeature });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(subscriptionFeature));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubscriptionFeatureBilling>>();
      const subscriptionFeature = { id: 'ABC' };
      jest.spyOn(subscriptionFeatureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subscriptionFeature });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subscriptionFeature }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(subscriptionFeatureService.update).toHaveBeenCalledWith(subscriptionFeature);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubscriptionFeatureBilling>>();
      const subscriptionFeature = new SubscriptionFeatureBilling();
      jest.spyOn(subscriptionFeatureService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subscriptionFeature });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subscriptionFeature }));
      saveSubject.complete();

      // THEN
      expect(subscriptionFeatureService.create).toHaveBeenCalledWith(subscriptionFeature);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubscriptionFeatureBilling>>();
      const subscriptionFeature = { id: 'ABC' };
      jest.spyOn(subscriptionFeatureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subscriptionFeature });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(subscriptionFeatureService.update).toHaveBeenCalledWith(subscriptionFeature);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
