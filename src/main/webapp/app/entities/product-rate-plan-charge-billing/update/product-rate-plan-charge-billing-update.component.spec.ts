jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ProductRatePlanChargeBillingService } from '../service/product-rate-plan-charge-billing.service';
import { IProductRatePlanChargeBilling, ProductRatePlanChargeBilling } from '../product-rate-plan-charge-billing.model';

import { ProductRatePlanChargeBillingUpdateComponent } from './product-rate-plan-charge-billing-update.component';

describe('ProductRatePlanChargeBilling Management Update Component', () => {
  let comp: ProductRatePlanChargeBillingUpdateComponent;
  let fixture: ComponentFixture<ProductRatePlanChargeBillingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productRatePlanChargeService: ProductRatePlanChargeBillingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ProductRatePlanChargeBillingUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ProductRatePlanChargeBillingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductRatePlanChargeBillingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productRatePlanChargeService = TestBed.inject(ProductRatePlanChargeBillingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const productRatePlanCharge: IProductRatePlanChargeBilling = { id: 'CBA' };

      activatedRoute.data = of({ productRatePlanCharge });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(productRatePlanCharge));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProductRatePlanChargeBilling>>();
      const productRatePlanCharge = { id: 'ABC' };
      jest.spyOn(productRatePlanChargeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productRatePlanCharge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productRatePlanCharge }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(productRatePlanChargeService.update).toHaveBeenCalledWith(productRatePlanCharge);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProductRatePlanChargeBilling>>();
      const productRatePlanCharge = new ProductRatePlanChargeBilling();
      jest.spyOn(productRatePlanChargeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productRatePlanCharge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productRatePlanCharge }));
      saveSubject.complete();

      // THEN
      expect(productRatePlanChargeService.create).toHaveBeenCalledWith(productRatePlanCharge);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProductRatePlanChargeBilling>>();
      const productRatePlanCharge = { id: 'ABC' };
      jest.spyOn(productRatePlanChargeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productRatePlanCharge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productRatePlanChargeService.update).toHaveBeenCalledWith(productRatePlanCharge);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
