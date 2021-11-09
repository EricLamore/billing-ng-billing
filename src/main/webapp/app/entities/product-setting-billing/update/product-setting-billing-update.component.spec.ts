jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ProductSettingBillingService } from '../service/product-setting-billing.service';
import { IProductSettingBilling, ProductSettingBilling } from '../product-setting-billing.model';

import { ProductSettingBillingUpdateComponent } from './product-setting-billing-update.component';

describe('ProductSettingBilling Management Update Component', () => {
  let comp: ProductSettingBillingUpdateComponent;
  let fixture: ComponentFixture<ProductSettingBillingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productSettingService: ProductSettingBillingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ProductSettingBillingUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ProductSettingBillingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductSettingBillingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productSettingService = TestBed.inject(ProductSettingBillingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const productSetting: IProductSettingBilling = { id: 'CBA' };

      activatedRoute.data = of({ productSetting });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(productSetting));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProductSettingBilling>>();
      const productSetting = { id: 'ABC' };
      jest.spyOn(productSettingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSetting });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productSetting }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(productSettingService.update).toHaveBeenCalledWith(productSetting);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProductSettingBilling>>();
      const productSetting = new ProductSettingBilling();
      jest.spyOn(productSettingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSetting });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productSetting }));
      saveSubject.complete();

      // THEN
      expect(productSettingService.create).toHaveBeenCalledWith(productSetting);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProductSettingBilling>>();
      const productSetting = { id: 'ABC' };
      jest.spyOn(productSettingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSetting });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productSettingService.update).toHaveBeenCalledWith(productSetting);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
