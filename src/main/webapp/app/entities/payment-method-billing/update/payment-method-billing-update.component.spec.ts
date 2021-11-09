jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PaymentMethodBillingService } from '../service/payment-method-billing.service';
import { IPaymentMethodBilling, PaymentMethodBilling } from '../payment-method-billing.model';

import { PaymentMethodBillingUpdateComponent } from './payment-method-billing-update.component';

describe('PaymentMethodBilling Management Update Component', () => {
  let comp: PaymentMethodBillingUpdateComponent;
  let fixture: ComponentFixture<PaymentMethodBillingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentMethodService: PaymentMethodBillingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PaymentMethodBillingUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(PaymentMethodBillingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentMethodBillingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paymentMethodService = TestBed.inject(PaymentMethodBillingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const paymentMethod: IPaymentMethodBilling = { id: 'CBA' };

      activatedRoute.data = of({ paymentMethod });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(paymentMethod));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentMethodBilling>>();
      const paymentMethod = { id: 'ABC' };
      jest.spyOn(paymentMethodService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentMethod });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentMethod }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(paymentMethodService.update).toHaveBeenCalledWith(paymentMethod);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentMethodBilling>>();
      const paymentMethod = new PaymentMethodBilling();
      jest.spyOn(paymentMethodService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentMethod });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentMethod }));
      saveSubject.complete();

      // THEN
      expect(paymentMethodService.create).toHaveBeenCalledWith(paymentMethod);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaymentMethodBilling>>();
      const paymentMethod = { id: 'ABC' };
      jest.spyOn(paymentMethodService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentMethod });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paymentMethodService.update).toHaveBeenCalledWith(paymentMethod);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
