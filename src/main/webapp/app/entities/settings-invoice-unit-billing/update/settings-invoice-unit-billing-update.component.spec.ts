jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SettingsInvoiceUnitBillingService } from '../service/settings-invoice-unit-billing.service';
import { ISettingsInvoiceUnitBilling, SettingsInvoiceUnitBilling } from '../settings-invoice-unit-billing.model';

import { SettingsInvoiceUnitBillingUpdateComponent } from './settings-invoice-unit-billing-update.component';

describe('SettingsInvoiceUnitBilling Management Update Component', () => {
  let comp: SettingsInvoiceUnitBillingUpdateComponent;
  let fixture: ComponentFixture<SettingsInvoiceUnitBillingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let settingsInvoiceUnitService: SettingsInvoiceUnitBillingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SettingsInvoiceUnitBillingUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(SettingsInvoiceUnitBillingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SettingsInvoiceUnitBillingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    settingsInvoiceUnitService = TestBed.inject(SettingsInvoiceUnitBillingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const settingsInvoiceUnit: ISettingsInvoiceUnitBilling = { id: 'CBA' };

      activatedRoute.data = of({ settingsInvoiceUnit });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(settingsInvoiceUnit));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SettingsInvoiceUnitBilling>>();
      const settingsInvoiceUnit = { id: 'ABC' };
      jest.spyOn(settingsInvoiceUnitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ settingsInvoiceUnit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: settingsInvoiceUnit }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(settingsInvoiceUnitService.update).toHaveBeenCalledWith(settingsInvoiceUnit);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SettingsInvoiceUnitBilling>>();
      const settingsInvoiceUnit = new SettingsInvoiceUnitBilling();
      jest.spyOn(settingsInvoiceUnitService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ settingsInvoiceUnit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: settingsInvoiceUnit }));
      saveSubject.complete();

      // THEN
      expect(settingsInvoiceUnitService.create).toHaveBeenCalledWith(settingsInvoiceUnit);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SettingsInvoiceUnitBilling>>();
      const settingsInvoiceUnit = { id: 'ABC' };
      jest.spyOn(settingsInvoiceUnitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ settingsInvoiceUnit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(settingsInvoiceUnitService.update).toHaveBeenCalledWith(settingsInvoiceUnit);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
