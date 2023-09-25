import { PropsWithChildren } from "react";

function SkeletonInlineWrapper({ children }: PropsWithChildren<unknown>) {
  return <span style={{ marginRight: "4.2rem" }}>{children}</span>;
}

export default SkeletonInlineWrapper;
