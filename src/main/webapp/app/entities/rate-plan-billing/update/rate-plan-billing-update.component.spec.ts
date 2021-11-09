jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RatePlanBillingService } from '../service/rate-plan-billing.service';
import { IRatePlanBilling, RatePlanBilling } from '../rate-plan-billing.model';

import { RatePlanBillingUpdateComponent } from './rate-plan-billing-update.component';

describe('RatePlanBilling Management Update Component', () => {
  let comp: RatePlanBillingUpdateComponent;
  let fixture: ComponentFixture<RatePlanBillingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ratePlanService: RatePlanBillingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [RatePlanBillingUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(RatePlanBillingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RatePlanBillingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ratePlanService = TestBed.inject(RatePlanBillingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const ratePlan: IRatePlanBilling = { id: 'CBA' };

      activatedRoute.data = of({ ratePlan });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(ratePlan));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RatePlanBilling>>();
      const ratePlan = { id: 'ABC' };
      jest.spyOn(ratePlanService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ratePlan });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ratePlan }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(ratePlanService.update).toHaveBeenCalledWith(ratePlan);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RatePlanBilling>>();
      const ratePlan = new RatePlanBilling();
      jest.spyOn(ratePlanService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ratePlan });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ratePlan }));
      saveSubject.complete();

      // THEN
      expect(ratePlanService.create).toHaveBeenCalledWith(ratePlan);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RatePlanBilling>>();
      const ratePlan = { id: 'ABC' };
      jest.spyOn(ratePlanService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ratePlan });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ratePlanService.update).toHaveBeenCalledWith(ratePlan);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
