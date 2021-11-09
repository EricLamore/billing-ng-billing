jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ConsumptionBillingService } from '../service/consumption-billing.service';
import { IConsumptionBilling, ConsumptionBilling } from '../consumption-billing.model';

import { ConsumptionBillingUpdateComponent } from './consumption-billing-update.component';

describe('ConsumptionBilling Management Update Component', () => {
  let comp: ConsumptionBillingUpdateComponent;
  let fixture: ComponentFixture<ConsumptionBillingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let consumptionService: ConsumptionBillingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ConsumptionBillingUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ConsumptionBillingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ConsumptionBillingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    consumptionService = TestBed.inject(ConsumptionBillingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const consumption: IConsumptionBilling = { id: 'CBA' };

      activatedRoute.data = of({ consumption });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(consumption));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ConsumptionBilling>>();
      const consumption = { id: 'ABC' };
      jest.spyOn(consumptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ consumption });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: consumption }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(consumptionService.update).toHaveBeenCalledWith(consumption);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ConsumptionBilling>>();
      const consumption = new ConsumptionBilling();
      jest.spyOn(consumptionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ consumption });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: consumption }));
      saveSubject.complete();

      // THEN
      expect(consumptionService.create).toHaveBeenCalledWith(consumption);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ConsumptionBilling>>();
      const consumption = { id: 'ABC' };
      jest.spyOn(consumptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ consumption });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(consumptionService.update).toHaveBeenCalledWith(consumption);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
