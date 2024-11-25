export interface Page<T> {
  content: Array<T>;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number; // Página atual (baseada em 0)
}
