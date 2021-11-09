jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ProductRatePlanBillingService } from '../service/product-rate-plan-billing.service';
import { IProductRatePlanBilling, ProductRatePlanBilling } from '../product-rate-plan-billing.model';

import { ProductRatePlanBillingUpdateComponent } from './product-rate-plan-billing-update.component';

describe('ProductRatePlanBilling Management Update Component', () => {
  let comp: ProductRatePlanBillingUpdateComponent;
  let fixture: ComponentFixture<ProductRatePlanBillingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productRatePlanService: ProductRatePlanBillingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ProductRatePlanBillingUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ProductRatePlanBillingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductRatePlanBillingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productRatePlanService = TestBed.inject(ProductRatePlanBillingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const productRatePlan: IProductRatePlanBilling = { id: 'CBA' };

      activatedRoute.data = of({ productRatePlan });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(productRatePlan));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProductRatePlanBilling>>();
      const productRatePlan = { id: 'ABC' };
      jest.spyOn(productRatePlanService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productRatePlan });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productRatePlan }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(productRatePlanService.update).toHaveBeenCalledWith(productRatePlan);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProductRatePlanBilling>>();
      const productRatePlan = new ProductRatePlanBilling();
      jest.spyOn(productRatePlanService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productRatePlan });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productRatePlan }));
      saveSubject.complete();

      // THEN
      expect(productRatePlanService.create).toHaveBeenCalledWith(productRatePlan);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProductRatePlanBilling>>();
      const productRatePlan = { id: 'ABC' };
      jest.spyOn(productRatePlanService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productRatePlan });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productRatePlanService.update).toHaveBeenCalledWith(productRatePlan);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
