jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CustomerBillingService } from '../service/customer-billing.service';
import { ICustomerBilling, CustomerBilling } from '../customer-billing.model';

import { CustomerBillingUpdateComponent } from './customer-billing-update.component';

describe('CustomerBilling Management Update Component', () => {
  let comp: CustomerBillingUpdateComponent;
  let fixture: ComponentFixture<CustomerBillingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let customerService: CustomerBillingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CustomerBillingUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(CustomerBillingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CustomerBillingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    customerService = TestBed.inject(CustomerBillingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const customer: ICustomerBilling = { id: 'CBA' };

      activatedRoute.data = of({ customer });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(customer));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CustomerBilling>>();
      const customer = { id: 'ABC' };
      jest.spyOn(customerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ customer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: customer }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(customerService.update).toHaveBeenCalledWith(customer);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CustomerBilling>>();
      const customer = new CustomerBilling();
      jest.spyOn(customerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ customer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: customer }));
      saveSubject.complete();

      // THEN
      expect(customerService.create).toHaveBeenCalledWith(customer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CustomerBilling>>();
      const customer = { id: 'ABC' };
      jest.spyOn(customerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ customer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(customerService.update).toHaveBeenCalledWith(customer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
