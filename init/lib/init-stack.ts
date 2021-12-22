import * as cdk from '@aws-cdk/core';
import * as ec2 from '@aws-cdk/aws-ec2';
import * as eks from '@aws-cdk/aws-eks';
import * as iam from '@aws-cdk/aws-iam';

export class InitStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const vpc = new ec2.Vpc(this, 'MainVPC', {
      cidr: "10.0.0.0/16",
      natGateways: 1,
      maxAzs: 2,
      subnetConfiguration: [
        {
          name: 'private-subnet-1',
          subnetType: ec2.SubnetType.PRIVATE_WITH_NAT,
          cidrMask: 24,
        },
        {
          name: 'public-subnet-1',
          subnetType: ec2.SubnetType.PUBLIC,
          cidrMask: 24,
        },
      ],
    });

    const cluster = new eks.Cluster(
      this, 'Cluster', {
          version: eks.KubernetesVersion.V1_21,
          vpc: vpc,
          vpcSubnets: [{ subnetType: ec2.SubnetType.PRIVATE_WITH_NAT }],
          defaultCapacity: 2,
          defaultCapacityInstance: ec2.InstanceType.of(ec2.InstanceClass.M5, ec2.InstanceSize.LARGE),
          albController: {
            version: eks.AlbControllerVersion.V2_3_0,
          },
      }
    );
    cluster.node.addDependency(vpc);
    
    const clusterAdimnUser = iam.User.fromUserAttributes(this, 'ClusterAdminUser', {
      userArn: `arn:aws:iam::${this.account}:user/${process.env.IAM_USER}`,
    });
  }
}
