jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RefundBillingService } from '../service/refund-billing.service';
import { IRefundBilling, RefundBilling } from '../refund-billing.model';

import { RefundBillingUpdateComponent } from './refund-billing-update.component';

describe('RefundBilling Management Update Component', () => {
  let comp: RefundBillingUpdateComponent;
  let fixture: ComponentFixture<RefundBillingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let refundService: RefundBillingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [RefundBillingUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(RefundBillingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RefundBillingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    refundService = TestBed.inject(RefundBillingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const refund: IRefundBilling = { id: 'CBA' };

      activatedRoute.data = of({ refund });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(refund));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RefundBilling>>();
      const refund = { id: 'ABC' };
      jest.spyOn(refundService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ refund });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: refund }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(refundService.update).toHaveBeenCalledWith(refund);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RefundBilling>>();
      const refund = new RefundBilling();
      jest.spyOn(refundService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ refund });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: refund }));
      saveSubject.complete();

      // THEN
      expect(refundService.create).toHaveBeenCalledWith(refund);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RefundBilling>>();
      const refund = { id: 'ABC' };
      jest.spyOn(refundService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ refund });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(refundService.update).toHaveBeenCalledWith(refund);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
