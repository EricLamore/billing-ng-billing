jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RatePlanChargeBillingService } from '../service/rate-plan-charge-billing.service';
import { IRatePlanChargeBilling, RatePlanChargeBilling } from '../rate-plan-charge-billing.model';

import { RatePlanChargeBillingUpdateComponent } from './rate-plan-charge-billing-update.component';

describe('RatePlanChargeBilling Management Update Component', () => {
  let comp: RatePlanChargeBillingUpdateComponent;
  let fixture: ComponentFixture<RatePlanChargeBillingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ratePlanChargeService: RatePlanChargeBillingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [RatePlanChargeBillingUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(RatePlanChargeBillingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RatePlanChargeBillingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ratePlanChargeService = TestBed.inject(RatePlanChargeBillingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const ratePlanCharge: IRatePlanChargeBilling = { id: 'CBA' };

      activatedRoute.data = of({ ratePlanCharge });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(ratePlanCharge));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RatePlanChargeBilling>>();
      const ratePlanCharge = { id: 'ABC' };
      jest.spyOn(ratePlanChargeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ratePlanCharge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ratePlanCharge }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(ratePlanChargeService.update).toHaveBeenCalledWith(ratePlanCharge);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RatePlanChargeBilling>>();
      const ratePlanCharge = new RatePlanChargeBilling();
      jest.spyOn(ratePlanChargeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ratePlanCharge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ratePlanCharge }));
      saveSubject.complete();

      // THEN
      expect(ratePlanChargeService.create).toHaveBeenCalledWith(ratePlanCharge);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RatePlanChargeBilling>>();
      const ratePlanCharge = { id: 'ABC' };
      jest.spyOn(ratePlanChargeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ratePlanCharge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ratePlanChargeService.update).toHaveBeenCalledWith(ratePlanCharge);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
