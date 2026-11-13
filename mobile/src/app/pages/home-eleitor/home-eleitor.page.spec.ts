import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomeEleitorPage } from './home-eleitor.page';

describe('HomeEleitorPage', () => {
  let component: HomeEleitorPage;
  let fixture: ComponentFixture<HomeEleitorPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeEleitorPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
